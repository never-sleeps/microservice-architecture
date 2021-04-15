package com.microservicearchitecture.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("kafka")
@EnableKafka
@Getter
@Setter
public class KafkaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class.getName());

    private String bootstrapAddress;

    private KafkaProperties kafkaProperties;

    @Autowired
    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @PostConstruct
    public void postConstruct() {
        if (bootstrapAddress == null) {
            throw new IllegalStateException("Kafka bootstrap url cannot be null!");
        }

        LOGGER.info("Kafka url: {}", bootstrapAddress);
    }

//    @Bean
//    public Map<String, Object> consumerConfigs() {
//
//        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
//        jsonDeserializer.addTrustedPackages("*");
//
//        Map<String, Object> props = new HashMap<>(
//                kafkaProperties.buildConsumerProperties()
//        );
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                jsonDeserializer);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG,
//                "order_group");
//
//        return props;
//    }

    // Json consumer configuration

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {

//        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
//        jsonDeserializer.addTrustedPackages("*");
//
//        return new DefaultKafkaConsumerFactory<>(
//                kafkaProperties.buildConsumerProperties(), new StringDeserializer(), jsonDeserializer
//        );

        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>(
                kafkaProperties.buildConsumerProperties()
        );
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                jsonDeserializer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "order_group");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // String Consumer Configuration

//    @Bean
//    public ConsumerFactory<String, String> stringConsumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(
//                kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new StringDeserializer()
//        );
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerStringContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(stringConsumerFactory());
//        return factory;
//    }
//
//    // Byte Array Consumer Configuration
//
//    @Bean
//    public ConsumerFactory<String, byte[]> byteArrayConsumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(
//                kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new ByteArrayDeserializer()
//        );
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerByteArrayContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(byteArrayConsumerFactory());
//        return factory;
//    }
}
