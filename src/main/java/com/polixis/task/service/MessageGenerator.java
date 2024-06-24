package com.polixis.task.service;

import com.polixis.task.model.Message;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Profile("messageGenerator")
@Component
@Log4j2
public class MessageGenerator {
    private ScheduledExecutorService pingProducerExecutor;
    private static final Duration PING_PERIOD = Duration.ofSeconds(30);
    private KafkaProducer<String, Message> pingProducer;
    @Autowired
    private KafkaProperties kafkaProperties;
    @Value("${kafka.servers}")
    private String kafkaServers;
    @Value("${kafka.input.topic}")
    private String inputTopic;

    @PostConstruct
    private void startPingProducer() {
        pingProducerExecutor = Executors.newSingleThreadScheduledExecutor();
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        props.putAll(kafkaProperties.getProperties());
        pingProducer = new KafkaProducer<>(props,
                new StringSerializer(), new JsonSerializer<>());
        pingProducerExecutor.scheduleAtFixedRate(() ->
                        pingProducer.send(new ProducerRecord<>(inputTopic,
                                Message.builder().content("ping").senderName("pingProducer").build())),
                PING_PERIOD.toMillis(), PING_PERIOD.toMillis(), TimeUnit.MILLISECONDS);
        log.info("Ping producer started...");
    }

}
