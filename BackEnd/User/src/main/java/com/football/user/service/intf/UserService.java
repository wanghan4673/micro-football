package com.football.user.service.intf;

import com.football.user.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User login(User user);
}
