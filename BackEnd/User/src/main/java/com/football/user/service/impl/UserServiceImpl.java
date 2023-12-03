package com.football.user.service.impl;
import com.football.user.mapper.UserMapper;
import com.football.user.model.User;
import com.football.user.service.intf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(User user) {
        return userMapper.getUserByAccAndPas(user);
    }

    @Override
    public Integer getScore(Long userId) {
        return userMapper.getScore(userId);
    }

    @Override
    public List<Map<String, Integer>> getFollowCount(Long userId) {
        return userMapper.getFollowCount(userId);
    }

    @Override
    public boolean updateUser(Long userId, String name, String signature, String email) {
        return userMapper.updateUser(userId,name,signature,email);
    }

    @Override
    @Transactional
    public boolean checkIn(Long userId) {
        try{
            boolean checkResult = userMapper.checkIn(userId);
            boolean checkDaysResult = userMapper.addCheckDay(userId);
            return checkResult && checkDaysResult;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public List<String> getCheckDays(Long userId) {
        // 数据库的类型为timestamp
        List<Timestamp> checkTimes = userMapper.getCheckTimes(userId);
        // 获取所有时间戳后将其转换为YYYY-MM-DD格式
        List<String> checkDays = new ArrayList<>();
        for(Timestamp checkTime : checkTimes){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String checkDay = sdf.format(new Date(checkTime.getTime()));
            checkDays.add(checkDay);
        }
        return checkDays;
    }
}
