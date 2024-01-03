package com.football.administrator.Controller;

import com.football.administrator.model.Result;
import com.football.mfapi.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    UserClient userClient;

    @GetMapping("/users")
    public Result getAllUser(){
        return Result.success(userClient.getAllUsers());
    }

    @GetMapping("/reported-users")
    public Result getReportedUser(){
        return Result.success(userClient.getBannedUsers());
    }

    @PostMapping("/status")
    public Result updateBanStatus(@RequestParam("id") Long id){
        userClient.updateBanStatus(id);
        return Result.success();
    }
}
