package com.transactions.kafkaTransactions.service;

import org.springframework.kafka.annotation.KafkaListener;

import static com.transactions.kafkaTransactions.config.KafkaTopicConfig.CREATE_CUSTOMER_TOPIC;
import static com.transactions.kafkaTransactions.config.KafkaTopicConfig.DELETE_CUSTOMER_TOPIC;

public class CustomerMessageHandler {
    @KafkaListener(topics = CREATE_CUSTOMER_TOPIC, groupId = "customer")
    public void listenToCreateCustomer(String message) {
        System.out.println("Received Message in group customer: " + message);
    }

    @KafkaListener(topics = DELETE_CUSTOMER_TOPIC, groupId = "customer")
    public void listenToDeleteCustomer(String message) {
        System.out.println("Received Message in group customer: " + message);
    }
}
