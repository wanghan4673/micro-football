package com.football.user.controller;

import com.football.common.utils.UserContext;
import com.football.user.model.Result;
import com.football.user.model.User;
import com.football.user.service.intf.UserService;
import com.football.user.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {
    // 登录 JWT令牌生成
    private final UserService userService;
    private final JwtUtils jwtUtils;
    @PostMapping("/login")
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
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        log.info("----------注册:{}----------", user);
        boolean register_result = userService.register(user);
        if(register_result){
            return login(user);
        } else{
            return Result.error("注册失败");
        }
    }
    @GetMapping("/loginStatus")
    public Result loginStatus(){
        // 首页的导航栏判断是否登录 如果登录获取头像和用户名
        Long userId = UserContext.getUser();
        log.info("----------判断登录:{}----------", userId);
        if(userId == null){
            return Result.success();
        }
        else{
            User user = userService.getNameAndAvatar(userId);
            log.info("new"+user);
            return Result.success(user);
        }
    }
}
