package io.enkrypt.kafka.listener;

import io.enkrypt.avro.capture.*;
import io.enkrypt.avro.common.Data20;
import io.enkrypt.avro.common.Data32;
import io.enkrypt.kafka.Kafka;
import io.enkrypt.kafka.mapping.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.ethereum.config.SystemProperties;
import org.ethereum.core.*;
import org.ethereum.db.ByteArrayWrapper;
import org.ethereum.listener.EthereumListener;
import org.ethereum.net.eth.message.StatusMessage;
import org.ethereum.net.message.Message;
import org.ethereum.net.p2p.HelloMessage;
import org.ethereum.net.rlpx.Node;
import org.ethereum.net.server.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.ByteBuffer.wrap;

public class KafkaEthereumListener implements EthereumListener {

  private static final Logger logger = LoggerFactory.getLogger("kafka-listener");

  private static final long SENTINEL_INTERVAL_MS = 1000;
  private static final int BATCH_SIZE = 32;

  private final Kafka kafka;
  private final SystemProperties config;
  private final ObjectMapper objectMapper;

  private final AtomicInteger numPendingTxs;

  private final ArrayBlockingQueue<BlockRecord> blockRecords;
  private final AtomicInteger numBlockRecords;
  private final AtomicBoolean draining;

  private final ScheduledExecutorService scheduledExecutor;

  private volatile ScheduledFuture<?> sentinelFuture;

  private volatile boolean shuttingDown = false;

  public KafkaEthereumListener(Kafka kafka, SystemProperties config, ObjectMapper objectMapper) {
    this.kafka = kafka;
    this.config = config;
    this.objectMapper = objectMapper;
    this.numPendingTxs = new AtomicInteger(0);
    this.draining = new AtomicBoolean(false);

    this.blockRecords = new ArrayBlockingQueue<>(BATCH_SIZE * 2);
    this.numBlockRecords = new AtomicInteger(0);

    this.scheduledExecutor = Executors.newScheduledThreadPool(1);
    this.sentinelFuture = scheduledExecutor.schedule(new Sentinel(), SENTINEL_INTERVAL_MS, TimeUnit.MILLISECONDS);
  }

  public void shutdown() throws InterruptedException {
    try {
      logger.info("Shutting down");

      shuttingDown = true;

      // wait for the sentinel to complete
      sentinelFuture.get(30, TimeUnit.SECONDS);

      // try a final drain
      tryToDrain();

      logger.info("Shutdown complete");
    } catch (ExecutionException | TimeoutException e) {
      e.printStackTrace();
    } finally {
      scheduledExecutor.shutdown();
      scheduledExecutor.awaitTermination(60, TimeUnit.SECONDS);
    }
  }

  @Override
  public void onPendingTransactionUpdate(TransactionReceipt txReceipt, PendingTransactionState state, Block block) {

    final byte[] txHash = txReceipt.getTransaction().getHash();

    final TransactionKeyRecord key = TransactionKeyRecord.newBuilder()
      .setTxHash(new Data32(txHash))
      .build();

    final KafkaProducer<TransactionKeyRecord, TransactionRecord> producer = kafka.getPendingTransactionsProducer();

    try {

      switch (state) {

        case DROPPED:
        case INCLUDED:
          // send a tombstone to 'remove' as any included transactions will be sent in the onBlock and
          // we no longer care about dropped transactions

          producer.send(new ProducerRecord<>(Kafka.TOPIC_PENDING_TRANSACTIONS, key, null)).get();
          numPendingTxs.decrementAndGet();
          break;

        case NEW_PENDING:
          final TransactionRecord record = objectMapper.convert(null, Transaction.class, TransactionRecord.class, txReceipt.getTransaction());
          producer.send(new ProducerRecord<>(Kafka.TOPIC_PENDING_TRANSACTIONS, key, record)).get();
          numPendingTxs.incrementAndGet();
          break;

        default:
          // Do nothing
      }

    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

  }

  @Override
  public void onBlock(BlockSummary blockSummary) {
    onBlock(blockSummary, false);
  }

  @Override
  public void onBlock(BlockSummary blockSummary, boolean best) {

    // TODO sync notification

    try {

      blockRecords.put(toRecord(blockSummary));

      if(shuttingDown || numBlockRecords.incrementAndGet() == BATCH_SIZE) {
        tryToDrain();
      }

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }

  private void tryToDrain() {

    logger.debug("Attempting to drain");

    if (draining.compareAndSet(false, true)) {

      logger.debug("Drain lock acquired");

      final KafkaProducer<BlockKeyRecord, BlockRecord> producer = kafka.getBlockProducer();

      try {

        final List<BlockRecord> batch = new ArrayList<>(BATCH_SIZE);

        while(blockRecords.drainTo(batch) > 0) {

          producer.beginTransaction();

          final List<Future<RecordMetadata>> futures = new ArrayList<>(batch.size());

          for (BlockRecord record : batch) {

            final ByteBuffer number = record.getHeader().getNumber();

            final BlockKeyRecord key = BlockKeyRecord.newBuilder()
              .setNumber(number)
              .build();

            // publish block summary

            futures.add(producer.send(new ProducerRecord<>(Kafka.TOPIC_BLOCKS, key, record)));
          }

          // wait on all the futures to complete and then commit

          for (Future<RecordMetadata> future : futures) {
            future.get(30, TimeUnit.SECONDS);
          }

          producer.commitTransaction();

          numBlockRecords.addAndGet(batch.size() * -1);

          logger.debug("{} block records published", batch.size());
        }

      } catch (ProducerFencedException ex) {

        logger.error("Fenced exception", ex);
        throw new RuntimeException(ex);

      } catch (Exception ex) {

        producer.abortTransaction();
        logger.error("Fatal exception", ex);
        throw new RuntimeException(ex);

      } finally {
        draining.set(false);
        logger.debug("Drain lock released");
      }
    }

  }

  private BlockRecord toRecord(BlockSummary blockSummary) {

    final Block block = blockSummary.getBlock();

    final BlockRecord.Builder builder = objectMapper.convert(null, BlockSummary.class, BlockRecord.Builder.class, blockSummary);

    builder.setNumPendingTxs(numPendingTxs.get());

    if (block.isGenesis()) {
      final Genesis genesis = Genesis.getInstance(config);

      final Map<ByteArrayWrapper, Genesis.PremineAccount> premine = genesis.getPremine();
      final List<PremineBalanceRecord> premineBalances = new ArrayList<>(premine.size());

      for (Map.Entry<ByteArrayWrapper, Genesis.PremineAccount> entry : premine.entrySet()) {

        final byte[] account = entry.getKey().getData();
        final AccountState accountState = entry.getValue().accountState;

        premineBalances.add(
          PremineBalanceRecord
            .newBuilder()
            .setAddress(new Data20(account))
            .setBalance(wrap(accountState.getBalance().toByteArray()))
            .build()
        );

      }

      builder.setPremineBalances(premineBalances);
    }

    return builder.build();
  }

  /**
   * Periodic task which attempts to drain the records
   */
  private final class Sentinel implements Runnable {
    @Override
    public void run() {
      try {
        tryToDrain();
      } catch(Throwable ex) {
        logger.error("Sentinel failure", ex);
        KafkaEthereumListener.this.sentinelFuture = scheduledExecutor.schedule(new Sentinel(), SENTINEL_INTERVAL_MS, TimeUnit.MILLISECONDS);
      }
    }
  }


  @Override
  public void trace(String output) {

  }

  @Override
  public void onNodeDiscovered(Node node) {

  }

  @Override
  public void onHandShakePeer(Channel channel, HelloMessage helloMessage) {

  }

  @Override
  public void onEthStatusUpdated(Channel channel, StatusMessage status) {

  }

  @Override
  public void onRecvMessage(Channel channel, Message message) {

  }

  @Override
  public void onSendMessage(Channel channel, Message message) {

  }

  @Override
  public void onPeerDisconnect(String host, long port) {

  }

  @Override
  public void onPendingTransactionsReceived(List<Transaction> transactions) {

  }

  @Override
  public void onPendingStateChanged(PendingState pendingState) {

  }

  @Override
  public void onSyncDone(SyncState state) {

  }

  @Override
  public void onNoConnections() {

  }

  @Override
  public void onVMTraceCreated(String transactionHash, String trace) {

  }

  @Override
  public void onTransactionExecuted(TransactionExecutionSummary summary) {

  }

  @Override
  public void onPeerAddedToSyncPool(Channel peer) {

  }
}
