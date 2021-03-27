package com.ptit.sqa.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaAdminConfig {

    @Value("${kafka.bootstrap.address}")
    private String kafkaServerAddress;
    @Value("${kafka.topic.event.mail}")
    private String eventSendMailTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createTopicEventSendMail(){
        return TopicBuilder
                .name(eventSendMailTopic)
                .partitions(2)
                .replicas(1)
                .compact()
                .build();
    }

}