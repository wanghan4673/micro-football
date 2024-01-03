package com.football.mfapi.client;

import com.football.mfapi.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/users")
    List<UserDTO> getAllUsers();

    @GetMapping("/users/banned")
    List<UserDTO> getBannedUsers();

    @PutMapping("/users/ban-status")
    Boolean updateBanStatus(@RequestParam("id") Long id);
    @PostMapping("/users/subscribe-game")
    Boolean subscribeGame(@RequestParam("userId") Long userId,
                       @RequestParam("gameId") Long gameId,
                       @RequestParam("startTime") String startTime);
}
