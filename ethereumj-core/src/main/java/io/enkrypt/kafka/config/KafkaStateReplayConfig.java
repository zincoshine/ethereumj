package io.enkrypt.kafka.config;

import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.enkrypt.kafka.Kafka;
import io.enkrypt.kafka.KafkaImpl;
import io.enkrypt.kafka.NullKafka;
import io.enkrypt.kafka.listener.KafkaEthereumListener;
import io.enkrypt.kafka.mapping.ObjectMapper;
import io.enkrypt.kafka.replay.StateReplayer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.ethereum.config.CommonConfig;
import org.ethereum.config.SystemProperties;
import org.ethereum.datasource.DbSettings;
import org.ethereum.datasource.DbSource;
import org.ethereum.datasource.Source;
import org.ethereum.datasource.rocksdb.RocksDbDataSource;
import org.ethereum.db.BlockStore;
import org.ethereum.db.IndexedBlockStore;
import org.ethereum.db.TransactionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class KafkaStateReplayConfig {

  private static Logger logger = LoggerFactory.getLogger("general");

  @Autowired
  private CommonConfig commonConfig;

  public KafkaStateReplayConfig() {
    // TODO: We can intercept KafkaException to stop completely the app in case of a bad crash
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.error("Uncaught exception", e));
  }

  @Bean
  public SystemProperties systemProperties() {
    return KafkaSystemProperties.getKafkaSystemProperties();
  }

  @Bean
  public BlockStore blockStore() {
    commonConfig.fastSyncCleanUp();
    IndexedBlockStore indexedBlockStore = new IndexedBlockStore();
    Source<byte[], byte[]> block = commonConfig.cachedDbSource("block");
    Source<byte[], byte[]> index = commonConfig.cachedDbSource("index");
    indexedBlockStore.init(index, block);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      indexedBlockStore.flush();
      indexedBlockStore.close();
    }));

    return indexedBlockStore;
  }

  @Bean
  public TransactionStore transactionStore() {
    commonConfig.fastSyncCleanUp();
    final TransactionStore txStore = new TransactionStore(commonConfig.cachedDbSource("transactions"));

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      txStore.flush();
      txStore.close();
    }));

    return txStore;
  }


  @Bean
  public StateReplayer stateReplayer() {
    return new StateReplayer();
  }

  DbSource<byte[]> dbSource(String name, DbSettings settings) {
    final RocksDbDataSource ds = new RocksDbDataSource();
    ds.setName(name);
    ds.init(settings);
    return ds;
  }

  @Bean
  public ExecutorService executorService() {
    final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      executor.shutdown();

      try {
        executor.awaitTermination(60, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        logger.warn("shutdown: executor interrupted: {}", e.getMessage());
      }

    }));

    return executor;
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public Kafka kafka(SystemProperties config) {

    final KafkaSystemProperties kafkaConfig = (KafkaSystemProperties) config;

    final boolean enabled = kafkaConfig.isKafkaEnabled();
    final String bootstrapServers = kafkaConfig.getKafkaBootstrapServers();

    if (!enabled) {
      return new NullKafka();
    }

    final Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "ethereumj-state-replayer");

    props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaConfig.getKafkaSchemaRegistryUrl());

    props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 2000000000);

    return new KafkaImpl(props);
  }

  @Bean
  public KafkaEthereumListener kafkaEthereumListener(Kafka kafka,
                                                     SystemProperties config,
                                                     ObjectMapper objectMapper) {

    final KafkaEthereumListener kafkaEthereumListener = new KafkaEthereumListener(kafka, config, objectMapper);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        kafkaEthereumListener.shutdown();
      } catch (InterruptedException e) {
        logger.warn("kafka ethereum listener shutdown: executor interrupted: {}", e.getMessage());
      }
    }));

    return kafkaEthereumListener;
  }
}
