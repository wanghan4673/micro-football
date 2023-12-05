package com.football.administrator.Service.Impl;

import com.football.administrator.Mapper.AdminLoginMapper;
import com.football.administrator.Service.Intf.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AdminLoginMapper adminLoginMapper;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;  // 发送者邮箱

    @Override
    public String checkEmail(String email) {
        return adminLoginMapper.getByEmail(email);
    }

    @Override
    public boolean addCode(String code,String id) {
        return adminLoginMapper.updateCode(code,id);
    }

    @Override
    public void sendEmail(String to, String subject, String context) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(context);
        mailSender.send(mailMessage);  // 正式发送邮件
    }

    @Override
    public String checkCode(String code, String email) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime appointTime = nowTime.minusMinutes(5);  // 判断现在时间的5分钟前和数据库中的时间 如果appointTime更晚 说明超时
        return adminLoginMapper.checkCode(code,email,appointTime);
    }
}
