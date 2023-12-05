package com.football.administrator.Service.Intf;

import org.springframework.stereotype.Service;

@Service
public interface AdminLoginService {
    String checkEmail(String email);

    boolean addCode(String code,String id);

    void sendEmail(String to, String subject, String context);

    String checkCode(String code, String email);
}
