package com.football.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class playerApplication {
    public static void main(String[] args) {
        SpringApplication.run(playerApplication.class, args);
        System.out.println("hello");
    }
}
