package org.prototype.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class KafkaExactlyOnceApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaExactlyOnceApplication.class, args);
    }
}
