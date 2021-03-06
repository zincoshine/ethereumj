package io.enkrypt.kafka.replay;

import io.enkrypt.kafka.Kafka;
import io.enkrypt.kafka.listener.KafkaEthereumListener;
import io.enkrypt.kafka.mapping.ObjectMapper;
import org.ethereum.config.SystemProperties;
import org.ethereum.core.*;
import org.ethereum.db.BlockStore;
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

public class StateReplayer {

  // to avoid using minGasPrice=0 from Genesis for the wallet
  private static final int MAGIC_REWARD_OFFSET = 8;

  @Autowired
  KafkaEthereumListener kafkaListener;

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

    final long maxNumber = parseLong(config.getProperty("replay.until", "" + Long.MAX_VALUE));

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

        kafkaListener.onBlock(blockSummary);

        if (number % 100 == 0) {
          logger.info("Replayed until number = {}", number);
        }

        number++;
      }

    } while (block != null && number <= maxNumber);

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

