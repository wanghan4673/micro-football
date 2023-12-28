package com.football.user.service.intf;

import com.football.user.model.AdminUsers;
import com.football.user.model.MyPost;
import com.football.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    User login(User user);

    User getUserCard(Long userId);

    List<Map<String, Integer>> getFollowCount(Long userId);

    boolean updateUser(Long userId, String name, String signature, String email);

    boolean checkIn(Long userId);

    List<String> getCheckDays(Long userId);

    User getNameAndAvatar(Long userId);

    boolean register(User user);

    List<MyPost> getMyPosts(Long userId);

    List<User> getMyFollowers(Long userId);

    List<User> getMyFans(Long userId);

    String getUserByIdAndPs(Long userId, String oriPassword);

    boolean updatePassword(Long userId, String newPassword);

    boolean deleteFollow(Long userId, Long deleteId);

    List<AdminUsers> getAllUsers();

    boolean updateBanStatus(Long id);
}
