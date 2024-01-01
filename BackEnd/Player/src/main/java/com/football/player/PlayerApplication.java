package com.football.player;
import com.football.player.Api.SearchGameApi;
import com.football.player.Api.SearchPlayerApi;
import com.football.player.Api.SearchSquadsApi;
import com.football.player.Api.SearchTeamApi;
import com.football.player.service.impl.GameServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlayerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlayerApplication.class, args);

//        System.out.println(new SearchGameApi().getGamesByDate("2020-02-06",200,2019));
        System.out.println(new GameServiceImpl().getGameDetailById(239625));
    }
}
