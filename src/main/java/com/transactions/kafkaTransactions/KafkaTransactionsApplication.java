package com.transactions.kafkaTransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class KafkaTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaTransactionsApplication.class, args);
	}

	/**
	 * Need to add this Bean to avoid ambiguous bean exception as spring was unable to determine if it should use transactionManager or kafkaTransactionManager
	 * @param entityManagerFactory
	 * @return
	 */
	@Bean
	@Primary
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
