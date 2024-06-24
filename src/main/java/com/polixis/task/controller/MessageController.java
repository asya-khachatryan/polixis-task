package com.polixis.task.controller;

import com.polixis.task.service.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageProducer messageProducer;

    @PostMapping
    public void sendMessage(@RequestParam String message) {
        messageProducer.send(message);
    }
}
