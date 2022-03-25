# Atomic Operations with Spring and Kafka Transaction Synchronization
 A simple Spring Boot Application to demo the workings of Spring + kafka transactions to support atomic operations.

## Dependencies
* Spring Kafka
* Postgres DB
* Kafka Broker and Zookeeper
* KafDrop - Optional (To view kafka messages and topics)

## Setup
1) Run `docker-compose -f src/main/KT-Docker/compose.yml up -d` to create Postrges, Kafka, KafDrop containers in Docker.
     - Re-run kafka container if it crashes the first time.
3) `mvn install -DskipTests` to download all spring dependencies.
4) Run the `KafkaTransactionsApplication` class to start the spring boot application.
     - This step will automatically create a `Customer` database table in postgres.
       - Customer Table has just two columns `id` and `name`.
     - This step will automatically create two kafka topics `create.customer` and `delete.customer`. 

## Usage
- To create a new Customer run `curl --url "http://localhost:8080/createTransactional?id=1&name=test"`
    - This will create save a new record in the DB with ID 1 and also publish a message to `create.customer` topic.  
### Producing a message
#### Testing atomic operations with a Transactional Kafka Template
- Run `curl --url "http://localhost:8080/createTransactional?id=1&name=test"` again for ID 1.
    - This request will throw a `DuplicateKeyException` which should rollback the kafka message published.
    - Since the DB transaciton and kafka transaciton are chained, either both are successfull or both fail making it an atomic operation.   

#### Testing with a Non-Transactional Kafka Template
- Run `curl --url "http://localhost:8080/createNonTransactional?id=1&name=test"` again for ID 1.
    - This request will throw a `DuplicateKeyException`.
    - Since we are non using a transactional kafka template, a new message is published to `create.customer` topic but the data is not saved to the database. 

### Consuming a message while producing another message
- Run `curl --url "http://localhost:8080/createNonTransactional?id=2&name=test"`.
    - This will publish a message to `create.customer` and this message will be read by the KafkaListener.
    - Since the Listener Container is chained with kafkaTransaction, any message produced using transactional Kafka template will synchronize with DB transacitons.

## References
- Spring + Kafka Transactions - https://docs.spring.io/spring-kafka/reference/html/#transaction-synchronization
- Kafka Transaction Coordinator - https://www.confluent.io/blog/transactions-apache-kafka/#:~:text=The%20transaction%20coordinator%20is%20a,its%20broker%20is%20the%20leader.
