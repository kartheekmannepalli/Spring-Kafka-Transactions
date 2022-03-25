package com.transactions.kafkaTransactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.transactions.kafkaTransactions.config.KafkaTopicConfig.CREATE_CUSTOMER_TOPIC;
import static com.transactions.kafkaTransactions.config.KafkaTopicConfig.DELETE_CUSTOMER_TOPIC;

@Service
public class CustomerMessageHandler {
    @Autowired
    KafkaTemplate<String, String> kafkaTransactionalTemplate;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    @KafkaListener(topics = CREATE_CUSTOMER_TOPIC, groupId = "customer")
    public void listenToCreateCustomerTransactional(String message) throws Exception {
        System.out.println("Received Message in group customer: " + message);
        //Note: This event will not be committed
        kafkaTransactionalTemplate.send(DELETE_CUSTOMER_TOPIC, "23");
        //Note: This event will be published even if there was an exception
        kafkaTemplate.send(DELETE_CUSTOMER_TOPIC, "45");
        throw new Exception("time pass");
    }

    @Transactional
    @KafkaListener(topics = DELETE_CUSTOMER_TOPIC, groupId = "customer")
    public void listenToDeleteCustomer(String message) {
        System.out.println("Received Message in group customer: " + message);
    }
}
