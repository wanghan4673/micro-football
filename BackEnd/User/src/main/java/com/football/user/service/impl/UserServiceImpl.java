package com.football.user.service.impl;
import com.football.user.mapper.UserMapper;
import com.football.user.model.User;
import com.football.user.service.intf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(User user) {
        return userMapper.getUserByAccAndPas(user);
    }
}
