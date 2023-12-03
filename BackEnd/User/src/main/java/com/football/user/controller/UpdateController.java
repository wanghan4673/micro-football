package com.football.user.controller;

import com.football.common.utils.UserContext;
import com.football.user.model.Result;
import com.football.user.service.intf.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UpdateController {
    // 因用户操作 更新用户信息
    @Autowired
    private UserService userService;
    @PostMapping("/update")
    public Result updateUser(@RequestParam(name="name",required = false) String name,
                             @RequestParam(name="signature",required = false)String signature,
                             @RequestParam(name="email",required = false)String email){
        // 还有一个参数是头像图片 暂时先不考虑
        Long userId = UserContext.getUser();
        log.info("----------查看积分:{}----------", userId);
        boolean updateResult = userService.updateUser(userId,name,signature,email);
        if(updateResult){
            return Result.success();
        } else{
            return Result.error("更新失败");
        }
    }

    @GetMapping("/checkIn")
    public Result checkIn(){
        // 签到交互即证明今日未签到 前端会通过getCheckDays来获取签到过的天数 从而自己判断是否签到过
        Long userId = UserContext.getUser();
        log.info("----------签到:{}----------", userId);
        boolean checkResult = userService.checkIn(userId);
        if(checkResult){
            return Result.success();
        } else{
            return Result.error("签到失败");
        }
    }
}