package org.prototype.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.prototype.kafka.model.Packet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaConfig {

  @Value("${spring.kafka.producer.bootstrap-servers}")
  private String kafkaBootstrapServers;

  @Value("${spring.kafka.producer.topic}")
  private String kafkaTopic;
  @Value("${spring.kafka.producer.group}")
  private String kafkaConsumerGroupId;

  @Bean
  public ProducerFactory<String, Packet> producerFactory(ObjectMapper objectMapper) {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
    config.put(ProducerConfig.BATCH_SIZE_CONFIG, 0);
    //config.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "TX-producer-0");
    //todo Timeout expired after 60000milliseconds while awaiting InitProducerId
    config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
    config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    config.put(ProducerConfig.ACKS_CONFIG, "all");
    config.put(ProducerConfig.RETRIES_CONFIG, 10);
    config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 300000);
    config.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);

    return new DefaultKafkaProducerFactory<>(config, new StringSerializer(),
        new JsonSerializer<>(objectMapper));
  }

  @Bean
  public KafkaTemplate<String, Packet> packetKafkaTemplate(ObjectMapper objectMapper) {
    KafkaTemplate<String, Packet> template = new KafkaTemplate<>(producerFactory(objectMapper));
    template.setDefaultTopic(kafkaTopic);
    return template;
  }

  @Bean
  public ConsumerFactory<String, String> createConsumerFactory() {
    Map<String, Object> config = new HashMap<>();

    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerGroupId);
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    config.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

    return new DefaultKafkaConsumerFactory<>(config);
  }

  @Bean(name = "kafkaListenerConsumerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerConsumerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(createConsumerFactory());
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    return factory;
  }

}
