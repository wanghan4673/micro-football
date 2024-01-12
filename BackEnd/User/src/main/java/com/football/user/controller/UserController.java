package com.football.user.controller;

import com.football.common.utils.UserContext;
import com.football.mfapi.client.AnnouncementClient;
import com.football.user.model.*;
import com.football.user.service.intf.UserService;
import com.football.user.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AnnouncementClient announcementClient;
    private final JwtUtils jwtUtils;


    /* POST */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("----------登录:{}----------", user);
        User login_user = userService.login(user);
        // 登录成功 生成令牌并下发令牌给前端
        if (login_user != null) {
            if (login_user.getIsbanned()) {
                return Result.error("isbanned");
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", login_user.getId());
            claims.put("account", login_user.getAccount());
            String jwt = jwtUtils.createJwt(claims);  // 生成令牌
            // 论坛需要favorite_league 故修改本处
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("jwt", jwt);
            responseData.put("favoriteLeague", login_user.getFavoriteLeague());
            responseData.put("id", login_user.getId());
            return Result.success(responseData);
        }
        return Result.error("userError");
        // 如果为null证明数据库中没有此用户
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        log.info("----------注册:{}----------", user);
        boolean register_result = userService.register(user);
        if (register_result) {
            return login(user);
        } else {
            return Result.error("注册失败");
        }
    }

    @PostMapping("/check-in")
    public Result checkIn() {
        // 签到交互即证明今日未签到 前端会通过getCheckDays来获取签到过的天数 从而自己判断是否签到过
        Long userId = UserContext.getUser();
        log.info("----------签到:{}----------", userId);
        boolean checkResult = userService.checkIn(userId);
        if (checkResult) {
            return Result.success();
        } else {
            return Result.error("签到失败");
        }
    }

    @PostMapping("/subscribe-game")
    public boolean followGame(@RequestParam("userId") Long userId,
                              @RequestParam("gameId") Long gameId,
                              @RequestParam("startTime") String startTime) {
        log.info("----------订阅赛事:{}.{},{}----------", userId, gameId, startTime);
        return userService.updateGameSubscript(userId, gameId, startTime);
    }

    /* PUT */
    @PutMapping()
    public Result updateUser(@RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "signature", required = false) String signature,
                             @RequestParam(name = "email", required = false) String email) {
        Long userId = UserContext.getUser();
        log.info("----------更新个人信息:{}----------", userId);
        log.info(name + signature + email);
        boolean updateResult = userService.updateUser(userId, name, signature, email);
        if (updateResult) {
            return Result.success();
        } else {
            return Result.error("更新失败");
        }
    }

    @PutMapping("/password")
    public Result changePassword(@RequestBody Passwords passwords) {
        Long userId = UserContext.getUser();
        log.info("----------修改密码:{},{}----------", userId, passwords.getOriPassword());
        String psCorrect = userService.getUserByIdAndPs(userId, passwords.getOriPassword());
        if (psCorrect != null) {
            // 存在用户且密码正确
            boolean changeResult = userService.updatePassword(userId, passwords.getNewPassword());
            if (changeResult) {
                return Result.success();
            } else {
                return Result.error("修改密码失败");
            }
        } else {
            return Result.error("原密码错误");
        }
    }

    @PutMapping("/ban-status")
    public boolean updateBanStatus(@RequestParam("id") Long id) {
        log.info("----------更新违禁状态:{}----------", id);
        return userService.updateBanStatus(id);
    }

    @PutMapping("/favorite-league")
    public Result updateLeague(@RequestParam(name = "league", required = false) String league) {
        Long userId = UserContext.getUser();
        log.info("----------更新最爱赛事:{},{}----------", userId, league);
        boolean gameResult = userService.updateLeague(league, userId);
        if (gameResult) {
            return Result.success();
        } else {
            return Result.error("关注失败");
        }
    }

    /* GET */
    @GetMapping("/login-status")
    public Result loginStatus() {
        // 首页的导航栏判断是否登录 如果登录获取头像和用户名
        Long userId = UserContext.getUser();
        log.info("----------判断登录状态:{}----------", userId);
        if (userId == null) {
            return Result.success();
        } else {
            User user = userService.getNameAndAvatar(userId);
            return Result.success(user);
        }
    }

    @GetMapping("/user-info")
    public Result getUserInfo() {
        // 获取个人信息(包括name,signature,score,favorite_league,follow,fans)
        Long userId = UserContext.getUser();
        log.info("----------查看个人信息:{}----------", userId);
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }

    @GetMapping("/user-detail")
    public Result getUserDetail(@RequestParam("userid") Long userid) {
        Long myid = UserContext.getUser();
        log.info("----------查看评论者信息:{}----------", userid);
        User user = userService.getCommentUser(userid,myid);
        return Result.success(user);
    }

    @PostMapping("/follow")
    public Result follow(@RequestParam("followerid") Long followerid){
        Long fansid = UserContext.getUser();
        log.info("----------关注用户:{}----------", fansid);
        userService.follow(fansid,followerid);
        return Result.success();
    }

    @GetMapping("/check-days")
    public Result getCheckDays() {
        // 获取签到天数
        Long userId = UserContext.getUser();
        log.info("----------获取签到天数:{}----------", userId);
        List<String> checkDays = userService.getCheckDays(userId);
        return Result.success(checkDays);
    }

    @GetMapping("/posts")
    public Result getMyPosts() {
        // 获取个人帖子
        Long userId = UserContext.getUser();
        log.info("----------获取我的帖子:{}----------", userId);
        List<MyPost> myPosts = userService.getMyPosts(userId);
        return Result.success(myPosts);
    }

    @GetMapping("/follow-list")
    public Result getFollowList() {
        // 获取关注列表
        Long userId = UserContext.getUser();
        log.info("----------获取关注列表:{}----------", userId);
        List<User> myFollowers = userService.getMyFollowers(userId);
        if (myFollowers != null) {
            return Result.success(myFollowers);
        } else {
            return Result.error("获取关注列表失败");
        }
    }

    @GetMapping("/fans-list")
    public Result getFansList() {
        // 获取粉丝列表
        Long userId = UserContext.getUser();
        log.info("----------获取粉丝列表:{}----------", userId);
        List<User> myFans = userService.getMyFans(userId);
        if (myFans != null) {
            return Result.success(myFans);
        } else {
            return Result.error("获取粉丝列表失败");
        }
    }

    @GetMapping("/notifications")
    public Result getNotifications() {
        // 获取通知
        Long userId = UserContext.getUser();
        log.info("----------获取通知:{}----------", userId);
        return Result.success(announcementClient.getAnnouncementById(userId));
    }

    @GetMapping()
    public List<AdminUsers> getAllUsers() {
        // 获取所有用户
        log.info("----------获取所有用户(为管理员准备)----------");
        List<AdminUsers> adminUsers = userService.getAllUsers();
        log.info(adminUsers.toString());
        return adminUsers;
    }

    @GetMapping("/banned")
    public List<AdminUsers> getBannedUsers() {
        // 获取所有被举报用户
        log.info("----------获取被举报用户(为管理员准备)----------");
        List<AdminUsers> adminUsers = userService.getBannedUsers();
        log.info(adminUsers.toString());
        return adminUsers;
    }

    @GetMapping("/upcoming-games")
    public Result getUpcomingGames() {
        // 获取即将开始的关注比赛
        Long userId = UserContext.getUser();
        log.info("----------获取即将开始的比赛:{}----------", userId);
//        String startTimeLimit = LocalDateTime.now().plusHours(24).toString();
        List<GameSubscription> upcomingGames = userService.getGamesByUserId(userId);
        return Result.success(upcomingGames);
    }

    @GetMapping("/completed-games")
    public Result getCompletedGames(){
        // 获取已经结束的关注比赛
        Long userId = UserContext.getUser();
        log.info("----------获取已经结束的关注比赛:{}----------", userId);
        List<GameSubscription> completedGames = userService.getCompletedGamesByUserId(userId);
        return Result.success(completedGames);
    }

    /* DELETE */
    @DeleteMapping("/follow/{deleteId}")
    public Result deleteFollow(@PathVariable Long deleteId) {
        Long userId = UserContext.getUser();
        log.info("----------取消关注:{},{}----------", userId, deleteId);
        boolean deleteResult = userService.deleteFollow(userId, deleteId);
        if (deleteResult) {
            return Result.success();
        } else {
            return Result.error("取消关注失败");
        }
    }
}
