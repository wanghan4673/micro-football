package com.football.user.Interceptor;

import com.football.user.utils.AuthContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取用户id
        String user_token_id = request.getHeader("user-token-id");
        if (user_token_id != null && !user_token_id.isEmpty()) {
            // 有用户id则存到ThreadLocal中
            AuthContext.setUser(Long.valueOf(user_token_id));
        }
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        AuthContext.removeUser();
    }
}
