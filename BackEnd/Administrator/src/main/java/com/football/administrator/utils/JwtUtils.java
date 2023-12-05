package com.football.administrator.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.token.signKey}")
    private String signKey;
    @Value("${jwt.token.expire}")
    private Long expire;

    // 生成JWT令牌并返回
    public String createJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)  // 第二部分
                .signWith(SignatureAlgorithm.HS256, signKey)  // 算法与密钥
                .setExpiration(new Date(System.currentTimeMillis() + expire))  // 过期时间
                .compact();
    }
}
