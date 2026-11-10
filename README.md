# Spring Kafka
## Description
This project is a very simple example of a Kafka application implemented in Spring.

The main purpose of this project is to learn how to use Kafka.

It has one producer and one consumer subscribed to one topic.

If the message is equal to "fail" a RuntimeException is being thrown and message lands in Dead Letter Queue. Then the consumer receives the message from dlq.

There is a controller, which sets an endpoint used to send messages.

## How to run
First of all run the docker-compose service, which will start a Kafka broker. Then just run the application from KafkaApplication class.

Example of a request:
POST localhost:8080/send/foo/someMessage