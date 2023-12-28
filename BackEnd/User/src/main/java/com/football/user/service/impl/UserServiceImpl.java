package com.football.user.service.impl;

import com.football.user.mapper.UserMapper;
import com.football.user.model.AdminUsers;
import com.football.user.model.MyPost;
import com.football.user.model.User;
import com.football.user.service.intf.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

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
            return true;
        } catch (Exception e) {
            return false;
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
        return userMapper.deleteFollow(userId,deleteId)&&userMapper.minusUserFollowNum(userId);
    }

    @Override
    public List<AdminUsers> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public boolean updateBanStatus(Long id) {
        return userMapper.updateBanStatus(id);
    }

    @Override
    public User getUserCard(Long userId) {
        return userMapper.getUserCard(userId);
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
        log.info("123"+checkTimes);
        for (Timestamp checkTime : checkTimes) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String checkDay = sdf.format(new Date(checkTime.getTime()));
            checkDays.add(checkDay);
        }
        log.info("456"+checkDays);
        return checkDays;
    }

    @Override
    public User getNameAndAvatar(Long userId) {
        return userMapper.getNameAndAvatar(userId);
    }

}
