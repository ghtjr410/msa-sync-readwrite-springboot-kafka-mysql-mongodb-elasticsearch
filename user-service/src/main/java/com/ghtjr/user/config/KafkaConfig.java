package com.ghtjr.user.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    @Bean
    public NewTopic userDeletedAccountTopic() {
        return new NewTopic(defaultTopic, 1, (short) 1);
    }
}
