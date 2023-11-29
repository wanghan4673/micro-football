package com.football.user.filter;

import com.alibaba.fastjson.JSONObject;
import com.football.user.model.Result;
import com.football.user.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/check/*")
public class CheckLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 强转类型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取请求头中的令牌(token)
        String token = request.getHeader("token");
        // 判断令牌是否存在
        if(token != null && !token.isEmpty()){
            log.info("令牌存在,开始解析");
            try {
                // 解析令牌
                new JwtUtils().parseJwt(token);
            } catch (Exception e) {
                log.info("解析失败,令牌不合法");
                Result login_error = Result.error("Not Logged In!");
                // 这里不像controller中可以使用@RestController将返回值转为json 故使用fastJSON
                String notLoggedIn = JSONObject.toJSONString(login_error);
                response.getWriter().write(notLoggedIn);
            }

        } else{
            log.info("令牌不存在");
            Result login_error = Result.error("Not Logged In!");
            String notLoggedIn = JSONObject.toJSONString(login_error);
            response.getWriter().write(notLoggedIn);
        }
        // 令牌存在且合法,放行
        log.info("令牌合法");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
