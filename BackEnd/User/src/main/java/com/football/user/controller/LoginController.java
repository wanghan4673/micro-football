package com.football.user.controller;

import com.football.user.model.Result;
import com.football.user.model.User;
import com.football.user.service.intf.UserService;
import com.football.user.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    @PostMapping("/user/login")
    public Result login(@RequestBody User user) {
        log.info("----------登录:{}----------", user);
        User login_user = userService.login(user);
        // 登录成功 生成令牌并下发令牌给前端
        if(login_user!=null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",login_user.getId());
            claims.put("account",login_user.getAccount());
            String jwt = jwtUtils.createJwt(claims);  // 生成令牌
            return Result.success(jwt);
        }
        return Result.error("用户名或密码错误");
        // 如果为null证明数据库中没有此用户
    }
}
