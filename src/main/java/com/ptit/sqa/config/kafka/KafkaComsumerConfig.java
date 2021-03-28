package com.ptit.sqa.config.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.model.CustomerInvoice;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaComsumerConfig {

    @Value("${kafka.bootstrap.address}")
    private String kafkaServerAddress;
    @Value("${kafka.consumer.groudid}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, CustomerInvoiceDTO> consumerFactory() {
        Map<String, Object> consumerProperties = new HashMap<>();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerAddress);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(
                consumerProperties,
                new StringDeserializer(),
                new JsonDeserializer<>(CustomerInvoiceDTO.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CustomerInvoiceDTO> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, CustomerInvoiceDTO> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        return containerFactory;
    }
}
