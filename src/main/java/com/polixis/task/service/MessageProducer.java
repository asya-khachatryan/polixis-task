package com.polixis.task.service;

import com.polixis.task.model.Message;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Log4j2
public class MessageProducer {
    @Autowired
    private KafkaProperties kafkaProperties;
    @Value("${kafka.servers}")
    private String kafkaServers;
    @Value("${kafka.input.topic}")
    private String inputTopic;

    private KafkaProducer<String, Message> producer;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        props.putAll(kafkaProperties.getProperties());
        producer = new KafkaProducer<>(props, new StringSerializer(), new JsonSerializer<>());
    }


    public void send(String message) {
        log.info("Sending message to kafka: {}", message);

        ProducerRecord<String, Message> messageRecord = new ProducerRecord<>(inputTopic, message,
                Message.builder().content(message).senderName("kafka").build());
        producer.send(messageRecord);
    }

    @PreDestroy
    public void close() {
        log.info("Stopping MessageProducer...");
        producer.close();
        log.info("MessageProducer stopped");
    }
}
