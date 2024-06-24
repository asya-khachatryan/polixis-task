package com.polixis.task.service;

import com.polixis.task.model.Message;
import com.polixis.task.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MessageService {
    private final MessageRepository messageRepository;

    @KafkaListener(topics = "${kafka.input.topic}", groupId = "${kafka.group.id}")
    public void listen(Message message) {
        log.info("Received message: " + message);
        messageRepository.save(message);
    }
}