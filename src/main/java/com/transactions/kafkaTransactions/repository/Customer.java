package com.transactions.kafkaTransactions.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Customer", schema = "public")
public class Customer {
    private int id;
    private String customerName;

    public Customer() {
    }

    public Customer(int id, String name) {
        this.id = id;
        this.customerName = name;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "customer_name")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
