package com.ghtjr.user.service;

import com.ghtjr.user.event.UserDeletedAccountEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeletedEventPublisher {
    private final KafkaTemplate<String, UserDeletedAccountEvent> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    public void publishUserDeletedEvent(String userUuid) {
        UserDeletedAccountEvent event = new UserDeletedAccountEvent(userUuid);
        kafkaTemplate.send(defaultTopic, event);
    }
}
