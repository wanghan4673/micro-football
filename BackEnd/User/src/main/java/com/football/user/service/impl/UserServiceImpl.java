package com.football.user.service.impl;

import com.football.user.mapper.UserMapper;
import com.football.user.model.AdminUsers;
import com.football.user.model.MyPost;
import com.football.user.model.User;
import com.football.user.service.intf.UserService;
import com.football.user.utils.TLSSigAPIv2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    private TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(1600013711, "8a1959745a1485707b6fa66ef37a20ccca6ad6e360834fc0b0a3c9c9efd916df");

    @Override
    public User login(User user) {
        return userMapper.getUserByAccAndPas(user);
    }

    @Override
    public boolean register(User user) {
        String account = user.getAccount();
        String name = user.getName();
        String password = user.getPassword();
        try {
            userMapper.insertUser(name, account, password);
            createIMAccount(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    private void createIMAccount(User user) {
        Random rand = new Random();
        long random = rand.nextInt() & 0xFFFFFFFFL;
        String url = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?sdkappid=1600013711&identifier=administrator&usersig=" + tlsSigAPIv2.genUserSig("administrator", 5184000) + "&random=" + random + "&contenttype=json";

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("UserID", user.getAccount());
        requestBody.put("Nick", user.getName());
//        requestBody.put("FaceUrl", ""); // 根据实际情况修改

        // 发送POST请求
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);

        // 检查响应状态码和内容
        if (response.getStatusCode() == HttpStatus.OK) {
            // 成功处理
            System.out.println("账号导入成功");
        } else {
            // 错误处理
            System.out.println("账号导入失败: " + response.getBody());
        }
    }


    @Override
    public List<MyPost> getMyPosts(Long userId) {
        return userMapper.getMyPosts(userId);
    }

    @Override
    @Transactional
    public List<User> getMyFollowers(Long userId) {
        try {
            List<Integer> followerIds = userMapper.getFollowerIds(userId);
            if (followerIds != null && !followerIds.isEmpty()) {
                return userMapper.getFollowersByIds(followerIds);
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            return null;  // 事务查询失败 返回null controller层再做一次判断
        }
    }

    @Override
    public List<User> getMyFans(Long userId) {
        try {
            List<Integer> fansIds = userMapper.getFansIds(userId);
            if (fansIds != null && !fansIds.isEmpty()) {
                return userMapper.getFansByIds(fansIds);
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            return null;  // 事务查询失败 返回null controller层再做一次判断
        }
    }

    @Override
    public String getUserByIdAndPs(Long userId, String oriPassword) {
        return userMapper.getUserByIdAndPs(userId, oriPassword);
    }

    @Override
    public boolean updatePassword(Long userId, String newPassword) {
        return userMapper.updatePassword(userId, newPassword);
    }

    @Override
    @Transactional
    public boolean deleteFollow(Long userId, Long deleteId) {
        return userMapper.deleteFollow(userId, deleteId) && userMapper.minusUserFollowNum(userId);
    }

    @Override
    public List<AdminUsers> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public List<AdminUsers> getBannedUsers() {
        return userMapper.getBannedUsers();
    }

    @Override
    public boolean updateLeague(String league, Long userId) {
        return userMapper.updateLeague(league, userId);
    }

    @Override
    public boolean updateGameSubscript(Long userId, Long gameId, String startTime) {
        return userMapper.insertGameBook(userId,gameId,startTime);
    }

    @Override
    public boolean updateBanStatus(Long id) {
        return userMapper.updateBanStatus(id);
    }

    @Override
    public User getUserInfo(Long userId) {
        return userMapper.getUserInfo(userId);
    }

    @Override
    public List<Map<String, Integer>> getFollowCount(Long userId) {
        return userMapper.getFollowCount(userId);
    }

    @Override
    public boolean updateUser(Long userId, String name, String signature, String email) {
        return userMapper.updateUser(userId, name, signature, email);
    }

    @Override
    @Transactional
    public boolean checkIn(Long userId) {
        try {
            boolean checkResult = userMapper.checkIn(userId);
            boolean checkDaysResult = userMapper.addCheckDay(userId);
            return checkResult && checkDaysResult;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> getCheckDays(Long userId) {
        // 数据库的类型为timestamp
        List<Timestamp> checkTimes = userMapper.getCheckTimes(userId);
        // 获取所有时间戳后将其转换为YYYY-MM-DD格式
        List<String> checkDays = new ArrayList<>();
        log.info("123" + checkTimes);
        for (Timestamp checkTime : checkTimes) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String checkDay = sdf.format(new Date(checkTime.getTime()));
            checkDays.add(checkDay);
        }
        log.info("456" + checkDays);
        return checkDays;
    }

    @Override
    public User getNameAndAvatar(Long userId) {
        return userMapper.getNameAndAvatar(userId);
    }

}
