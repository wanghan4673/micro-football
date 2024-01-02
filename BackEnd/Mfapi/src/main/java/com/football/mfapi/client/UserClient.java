package com.football.mfapi.client;

import com.football.mfapi.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/user/getAllUsers")
    List<UserDTO> getAllUsers();

    @GetMapping("/user/getBannedUsers")
    List<UserDTO> getBannedUsers();

    @GetMapping("/user/updateBanStatus")
    Boolean updateBanStatus(@RequestParam("id") Long id);
    @PostMapping("/user/followGame")
    Boolean followGame(@RequestParam("userId") Long userId,
                       @RequestParam("gameId") Long gameId,
                       @RequestParam("startTime") String startTime);
}
