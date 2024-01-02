package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.AdminLoginService;
import com.football.administrator.model.Administrator;
import com.football.administrator.model.Result;
import com.football.administrator.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;
    @Autowired
    private JwtUtils jwtUtils;
    // 登录
    @PostMapping("/login/getCode")
    public Result loginGetCode(@RequestBody Administrator admin) {
        log.info("----------管理员获取验证码----------"+admin);
        // 1.判断输入的邮箱在数据库中是否存在
        String adminId = adminLoginService.checkEmail(admin.getEmail());
        if (adminId == null)
            return Result.error("这不是管理员邮箱!");
        else {
            // 2.生成4位随机验证码 并保存到数据库
            String code = generateRandomCode();
            boolean addCode = adminLoginService.addCode(code,adminId);
            if(!addCode)
                return Result.error("生成验证码失败");
            else{
                // 3.将验证码发送到邮箱中
                String email = admin.getEmail();
                String subject = "足球网站登录验证码";
                String context = "尊敬的管理员"+email+",您本次的登录验证码为:"+code+",五分钟内有效,请妥善保管!";
                adminLoginService.sendEmail(email,subject,context);
                return Result.success("验证码已发送");
            }
        }
    }

    @PostMapping("/login/checkCode")
    public Result loginCheckCode(@RequestBody Administrator admin){
        // 4.判断输入验证码和数据库对应验证码是否相同 并判断接收时间是否超过5分钟
        log.info("----------管理员校验验证码----------"+admin);
        String adminId = adminLoginService.checkCode(admin.getCode(),admin.getEmail());
        if(adminId == null)
            return Result.error("验证码不正确或超出有效时间!");
        else{
            // 5.生成token并下发
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",Long.valueOf(adminId));
            String jwt = jwtUtils.createJwt(claims);
            return Result.success(jwt);
        }
    }

    private String generateRandomCode() {
        //生成0000-9999的验证码
        Random random = new Random();
        int randomFour = random.nextInt(10000);
        return String.format("%04d", randomFour);
    }
}
