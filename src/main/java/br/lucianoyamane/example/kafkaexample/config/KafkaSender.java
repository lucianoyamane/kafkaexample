package br.lucianoyamane.example.kafkaexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topicRequest, String message)  {
        kafkaTemplate.send(topicRequest, message);
    }

}
