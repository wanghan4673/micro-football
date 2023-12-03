package com.football.user;

import com.football.common.config.UserConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(UserConfig.class)
public class userApplication {
    public static void main(String[] args) {
        SpringApplication.run(userApplication.class, args);
    }
}