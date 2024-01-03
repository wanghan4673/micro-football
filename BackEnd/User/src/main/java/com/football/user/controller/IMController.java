package com.football.user.controller;

import com.football.user.model.Result;
import com.football.user.model.User;
import com.football.user.service.intf.UserService;
import com.football.user.utils.TLSSigAPIv2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class IMController {

    private TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(1600013711,"8a1959745a1485707b6fa66ef37a20ccca6ad6e360834fc0b0a3c9c9efd916df");

    private final UserService userService;
    @GetMapping("/usersig")
    public Result getIMUsersig(@RequestBody String userid){
        return Result.success(tlsSigAPIv2.genUserSig("administrator",5184000));
    }
}
