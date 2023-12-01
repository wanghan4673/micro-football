package com.football.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    @Value("${jwt.token.signKey}")
    private static String signKey;

    // 解析JWT令牌
    public Long parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)  // 使用密钥
                .parseClaimsJws(jwt)  // 解析
                .getBody();
        return claims.get("id", Long.class);
    }
}
