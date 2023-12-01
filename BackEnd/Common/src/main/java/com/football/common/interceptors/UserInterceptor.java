package com.football.common.interceptors;

import com.football.common.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserInterceptor implements HandlerInterceptor {
    // 不需要做登录拦截 因为已经在网关中实现 在这里只获取用户id 方便其它微服务模块获取
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取用户id
        String user_token_id = request.getHeader("user-token-id");
        if (user_token_id != null && !user_token_id.isEmpty()) {
            // 有用户id则存到ThreadLocal中
            UserContext.setUser(Long.valueOf(user_token_id));
        }
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.removeUser();
    }
}
