package com.football.player;
import com.football.player.Api.SearchPlayerApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlayerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlayerApplication.class, args);

        System.out.println(new SearchPlayerApi().getResponseEntityByKey("bill",39));
    }
}
