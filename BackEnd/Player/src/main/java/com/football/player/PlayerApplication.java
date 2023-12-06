package com.football.player;
import com.football.player.api.searchPlayerApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.football.player.api.searchTeamApi;

@SpringBootApplication
@EnableDiscoveryClient
public class PlayerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlayerApplication.class, args);

        System.out.println(new searchPlayerApi().getResponseEntityByKey("bill",39));
    }
}
