package io.enkrypt.kafka.config;

import io.enkrypt.kafka.Kafka;
import io.enkrypt.kafka.listener.KafkaEthereumListener;
import io.enkrypt.kafka.mapping.ObjectMapper;
import org.ethereum.config.SystemProperties;
import org.ethereum.listener.CompositeEthereumListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import({KafkaConfig.class})
public class KafkaEthereumConfig {

  private static Logger logger = LoggerFactory.getLogger("kafka");

  public KafkaEthereumConfig() {
    // TODO: We can intercept KafkaException to stop completely the app in case of a bad crash
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.error("Uncaught exception", e));
  }

  @Bean
  @Primary
  public CompositeEthereumListener ethereumListener(Kafka kafka,
                                                    SystemProperties config,
                                                    ObjectMapper objectMapper) {

    final CompositeEthereumListener compositeListener = new CompositeEthereumListener();

    if(!kafka.isEnabled()) return compositeListener;

    final KafkaEthereumListener kafkaEthereumListener = new KafkaEthereumListener(kafka, config, objectMapper);
    compositeListener.addListener(kafkaEthereumListener);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        kafkaEthereumListener.shutdown();
      } catch (InterruptedException e) {
        logger.warn("kafka ethereum listener shutdown: executor interrupted: {}", e.getMessage());
      }
    }));

    return compositeListener;
  }

}
