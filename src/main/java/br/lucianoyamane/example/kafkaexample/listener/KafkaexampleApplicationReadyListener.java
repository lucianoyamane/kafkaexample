package br.lucianoyamane.example.kafkaexample.listener;

import br.lucianoyamane.example.kafkaexample.config.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Order(0)
public class KafkaexampleApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${testcase.threads:5}")
    private int threads;

    @Value("${testcase.requests:10}")
    private int requests;

    @Value("${message.topic.foo}")
    private String topicFoo;

    @Autowired
    KafkaSender kafkaSender;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < requests; i++) {
            Runnable worker = new MyRunnable(String.valueOf(i));
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
        System.out.println("\nFinished all threads");
    }

    public class MyRunnable implements Runnable {
        private final String index;

        MyRunnable(String index) {
            this.index = index;
        }

        @Override
        public void run() {
            try {
                kafkaSender.send(topicFoo, "foo: " + index);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
