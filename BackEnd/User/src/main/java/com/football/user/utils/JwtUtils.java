package com.football.user.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    @Value("${jwt.token.signKey}")
    private static String signKey;
    @Value("${jwt.token.expire}")
    private static Long expire;

    // 生成JWT令牌并返回
    public static String createJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)  // 第二部分
                .signWith(SignatureAlgorithm.HS256, signKey)  // 算法与密钥
                .setExpiration(new Date(System.currentTimeMillis() + expire))  // 过期时间
                .compact();
    }

    // 解析JWT令牌
    public static void parseJwt(String jwt) {
        Jwts.parser()
                .setSigningKey(signKey)  // 使用密钥
                .parseClaimsJws(jwt)  // 解析
                .getBody();
    }
}
