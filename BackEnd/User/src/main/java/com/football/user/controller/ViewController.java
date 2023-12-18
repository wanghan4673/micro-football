package com.football.user.controller;

import com.football.common.utils.UserContext;
import com.football.user.model.MyPost;
import com.football.user.model.Result;
import com.football.user.model.User;
import com.football.user.service.intf.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class ViewController {
    // 因用户操作 预览用户信息
    @Autowired
    private UserService userService;
    @GetMapping("/score")
    public Result viewScore(){
        Long userId = UserContext.getUser();
        log.info("----------查看积分:{}----------", userId);
        Integer score = userService.getScore(userId);
        return Result.success(score);
    }
    @GetMapping("/followCount")
    public Result viewFollowCount(){
        Long userId = UserContext.getUser();
        log.info("----------查看关注人数:{}----------", userId);
        List<Map<String, Integer>> followCount = userService.getFollowCount(userId);
        return Result.success(followCount);
    }

    @GetMapping("/getCheckDays")
    public Result viewCheckDays(){
        Long userId = UserContext.getUser();
        log.info("----------获取签到天数:{}----------", userId);
        List<String> checkDays = userService.getCheckDays(userId);
        return Result.success(checkDays);
    }

    @GetMapping("/getMyPosts")
    public Result viewMyPosts(){
        Long userId = UserContext.getUser();
        log.info("----------获取我的帖子:{}----------", userId);
        List<MyPost> myPosts = userService.getMyPosts(userId);
        return Result.success(myPosts);
    }

    @GetMapping("/getFollowDetail")
    public Result viewFollowDetail(){
        Long userId = UserContext.getUser();
        log.info("----------获取关注列表:{}----------", userId);
        List<User> myFollowers = userService.getMyFollowers(userId);
        if(myFollowers != null){
            return Result.success(myFollowers);
        } else{
            return Result.error("获取关注列表失败");
        }
    }

    @GetMapping("/getFansDetail")
    public Result viewFansDetail(){
        Long userId = UserContext.getUser();
        log.info("----------获取粉丝列表:{}----------", userId);
        List<User> myFans = userService.getMyFans(userId);
        if(myFans != null){
            return Result.success(myFans);
        } else{
            return Result.error("获取粉丝列表失败");
        }
    }
}
