package com.transactions.kafkaTransactions.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    public static final String CREATE_CUSTOMER_TOPIC = "create.customer";
    public static final String DELETE_CUSTOMER_TOPIC = "delete.customer";
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createCustomer() {
        return new NewTopic(CREATE_CUSTOMER_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic deleteCustomer() {
        return new NewTopic(DELETE_CUSTOMER_TOPIC, 1, (short) 1);
    }
}