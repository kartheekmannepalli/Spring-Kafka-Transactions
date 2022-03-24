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

    @RequestMapping("/createTransactional")
    public String createCustomer(@RequestParam Integer id, @RequestParam String name) {
        this.transactionService.createTransactional(id, name);
        return "Created Customer";
    }

    @RequestMapping("/createNonTransactional")
    public String createCustomer2(@RequestParam Integer id, @RequestParam String name) {
        this.transactionService.createNonTransactional(id, name);
        return "Created Customer";
    }

    @RequestMapping("/deleteTransactional/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        this.transactionService.deleteTransactional(id);
        return "Created Customer";
    }

    @RequestMapping("/deleteNonTransactional/{id}")
    public String deleteCustomer2(@PathVariable Integer id) {
        this.transactionService.deleteNonTransactional(id);
        return "Created Customer";
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class, DuplicateKeyException.class })
    public String handleException(Exception ex) {
        ex.printStackTrace();
        return ex.getMessage();
    }
}
