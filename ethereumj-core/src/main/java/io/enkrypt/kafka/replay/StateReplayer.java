package io.enkrypt.kafka.replay;

import io.enkrypt.avro.capture.BlockRecord;
import io.enkrypt.avro.capture.PremineBalanceRecord;
import io.enkrypt.avro.common.Data20;
import io.enkrypt.kafka.Kafka;
import io.enkrypt.kafka.listener.KafkaBlockSummaryPublisher;
import io.enkrypt.kafka.mapping.ObjectMapper;
import org.ethereum.config.SystemProperties;
import org.ethereum.core.*;
import org.ethereum.db.BlockStore;
import org.ethereum.db.ByteArrayWrapper;
import org.ethereum.db.IndexedBlockStore;
import org.ethereum.db.TransactionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static java.lang.Long.parseLong;
import static java.nio.ByteBuffer.wrap;
import static org.ethereum.core.Denomination.SZABO;

public class StateReplayer {

  // to avoid using minGasPrice=0 from Genesis for the wallet
  private static final long INITIAL_MIN_GAS_PRICE = 10 * SZABO.longValue();
  private static final int MAGIC_REWARD_OFFSET = 8;

  @Autowired
  KafkaBlockSummaryPublisher blockListener;

  @Autowired
  BlockStore blockStore;

  @Autowired
  TransactionStore txStore;

  @Autowired
  SystemProperties config;

  @Autowired
  Kafka kafka;

  @Autowired
  ExecutorService executor;

  @Autowired
  private ObjectMapper objectMapper;

  private final Logger logger = LoggerFactory.getLogger("state-replay");

  public StateReplayer() {
  }

  public void replay() {

    final IndexedBlockStore blockStore = (IndexedBlockStore) this.blockStore;

    Block block;
    long number = parseLong(config.getProperty("replay.from", "0"));

    logger.info("Attempting to replay from number = {}", number);

    do {

      block = blockStore.getChainBlockByNumber(number);

      if (block != null) {

        final List<Transaction> txs = block.getTransactionsList();
        final List<TransactionReceipt> receipts = new ArrayList<>(txs.size());
        final List<TransactionExecutionSummary> executionSummaries = new ArrayList<>(txs.size());

        for (Transaction tx : txs) {

          final TransactionInfo txInfo = txStore.get(tx.getHash(), block.getHash());

          // need to rehydrate tx reference

          final TransactionReceipt receipt = txInfo.getReceipt();
          receipt.setTransaction(tx);
          receipts.add(receipt);

          final TransactionExecutionSummary executionSummary =
            new TransactionExecutionSummary.Builder(txInfo.getExecutionSummary())
              .tx(tx)
              .build();

          executionSummaries.add(executionSummary);
        }

        final Map<byte[], BigInteger> rewards = calculateRewards(block, executionSummaries);
        final BlockSummary blockSummary = new BlockSummary(block, rewards, receipts, executionSummaries);

        final BlockRecord.Builder builder = objectMapper
          .convert(null, BlockSummary.class, BlockRecord.Builder.class, blockSummary);

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

        blockListener.onBlock(builder.build());

        if (number % 1000 == 0) {
          logger.info("Replayed until number = {}", number);
        }

        number++;
      }

    } while (block != null);

    logger.info("Replay complete, last number = {}", number - 1);

    System.exit(0);


  }

  private Map<byte[], BigInteger> calculateRewards(Block block, List<TransactionExecutionSummary> summaries) {

    Map<byte[], BigInteger> rewards = new HashMap<>();

    BigInteger blockReward = config.getBlockchainConfig().getConfigForBlock(block.getNumber()).getConstants().getBLOCK_REWARD();
    BigInteger inclusionReward = blockReward.divide(BigInteger.valueOf(32));

    // Add extra rewards based on number of uncles
    if (block.getUncleList().size() > 0) {
      for (BlockHeader uncle : block.getUncleList()) {
        final BigInteger uncleReward = blockReward
          .multiply(BigInteger.valueOf(MAGIC_REWARD_OFFSET + uncle.getNumber() - block.getNumber()))
          .divide(BigInteger.valueOf(MAGIC_REWARD_OFFSET));

        final BigInteger existingUncleReward = rewards.get(uncle.getCoinbase());
        if (existingUncleReward == null) {
          rewards.put(uncle.getCoinbase(), uncleReward);
        } else {
          rewards.put(uncle.getCoinbase(), existingUncleReward.add(uncleReward));
        }
      }
    }

    final BigInteger minerReward = blockReward.add(inclusionReward.multiply(BigInteger.valueOf(block.getUncleList().size())));

    BigInteger totalFees = BigInteger.ZERO;
    for (TransactionExecutionSummary summary : summaries) {
      totalFees = totalFees.add(summary.getFee());
    }
    rewards.put(block.getCoinbase(), minerReward.add(totalFees));
    return rewards;
  }

}

