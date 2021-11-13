package br.lucianoyamane.example.kafkaexample.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FooTopicListener {

    @KafkaListener(topics = "${message.topic.foo}", groupId = "${group.foo}")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
