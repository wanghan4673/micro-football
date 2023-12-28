package com.football.mfapi.client;

import com.football.mfapi.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/user/getAllUsers")
    List<UserDTO> getAllUsers();

    @GetMapping("/user/updateBanStatus")
    Boolean updateBanStatus(@RequestParam("id") Long id);
}
