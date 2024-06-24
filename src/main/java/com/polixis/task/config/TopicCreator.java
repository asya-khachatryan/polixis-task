package com.polixis.task.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@Log4j2
public class TopicCreator {
    @Autowired
    private KafkaProperties kafkaProperties;
    @Value("${kafka.input.topic}")
    private String inputTopic;
    @Value("${kafka.servers}")
    private String kafkaServers;

    @PostConstruct
    public void createTopic() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        properties.putAll(this.kafkaProperties.getProperties());
        KafkaAdmin kafkaAdmin = new KafkaAdmin(properties);
        NewTopic topic = new NewTopic(inputTopic, 1, (short) 1);
        log.info("Trying to create topic:" + topic);
        kafkaAdmin.createOrModifyTopics(topic);
    }
}