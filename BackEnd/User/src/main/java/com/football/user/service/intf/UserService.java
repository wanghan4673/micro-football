package com.football.user.service.intf;

import com.football.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    User login(User user);

    Integer getScore(Long userId);

    List<Map<String, Integer>> getFollowCount(Long userId);

    boolean updateUser(Long userId, String name, String signature, String email);

    boolean checkIn(Long userId);

    List<String> getCheckDays(Long userId);

    User getNameAndAvatar(Long userId);

    boolean register(User user);
}
