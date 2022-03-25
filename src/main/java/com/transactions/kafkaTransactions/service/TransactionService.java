package com.transactions.kafkaTransactions.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transactions.kafkaTransactions.repository.Customer;
import com.transactions.kafkaTransactions.repository.CustomerRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.transactions.kafkaTransactions.config.KafkaTopicConfig.CREATE_CUSTOMER_TOPIC;
import static com.transactions.kafkaTransactions.config.KafkaTopicConfig.DELETE_CUSTOMER_TOPIC;

@Service
public class TransactionService {
    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, String> kafkaTransactionalTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public TransactionService(CustomerRepository customerRepository, KafkaTemplate<String,
            String> kafkaTransactionalTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.kafkaTransactionalTemplate = kafkaTransactionalTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void createTransactional(int id, String name) {
        //Note: This kafka message will not be committed
        kafkaTransactionalTemplate.send(CREATE_CUSTOMER_TOPIC, getJsonString(id, name));
        if(customerRepository.existsById(id))
            throw new DuplicateKeyException("Customer Already Exists");
        Customer customer = new Customer(id, name);
        customerRepository.save(customer);
    }

    @Transactional
    public void createNonTransactional(int id, String name) {
        kafkaTemplate.send(CREATE_CUSTOMER_TOPIC, getJsonString(id, name));
        if(customerRepository.existsById(id))
            throw new DuplicateKeyException("Customer Already Exists");
        Customer customer = new Customer(id, name);
        customerRepository.save(customer);
    }

    @Transactional
    public void deleteTransactional(Integer id) {
        //Note: This kafka message will not be committed if delete throws an exception
        kafkaTransactionalTemplate.send(DELETE_CUSTOMER_TOPIC, String.valueOf(id));
        customerRepository.deleteById(id);
    }

    @Transactional
    public void deleteNonTransactional(Integer id) {
        kafkaTemplate.send(DELETE_CUSTOMER_TOPIC, String.valueOf(id));
        customerRepository.deleteById(id);
    }

    private String getJsonString(int id, String name) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", id);
        node.put("customerName", name);
        return node.toString();
    }
}
