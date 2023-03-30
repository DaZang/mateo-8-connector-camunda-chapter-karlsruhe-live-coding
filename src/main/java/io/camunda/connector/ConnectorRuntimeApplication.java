package io.camunda.connector;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableZeebeClient
public class ConnectorRuntimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConnectorRuntimeApplication.class, args);
    }
}
