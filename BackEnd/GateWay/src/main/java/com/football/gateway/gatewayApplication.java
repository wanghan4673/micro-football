package com.football.gateway;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("org.springdoc.core")
public class gatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(gatewayApplication.class, args);
    }
}
