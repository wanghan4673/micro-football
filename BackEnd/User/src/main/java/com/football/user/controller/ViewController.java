package com.football.user.controller;

import com.football.user.service.intf.UserService;
import com.football.user.utils.AuthContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ViewController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/score")
    public Integer showScore(){
        Long userId = AuthContext.getUser();
        log.info("userId is: "+userId);
        return 1;
    }
}