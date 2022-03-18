package com.transactions.kafkaTransactions.controller;

import com.transactions.kafkaTransactions.service.TransactionService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping("/")
    public String get() {
        return "Spring & Kafka Transaction Test application";
    }

    @RequestMapping("/createCustomer")
    public String createCustomer(@RequestParam Integer id, @RequestParam String name) {
        this.transactionService.createCustomer(id, name);
        return "Created Customer";
    }

    @RequestMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        this.transactionService.deleteCustomer(id);
        return "Created Customer";
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class, DuplicateKeyException.class })
    public String handleException(Exception ex) {
        ex.printStackTrace();
        return ex.getMessage();
    }
}
