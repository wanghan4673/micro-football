package com.football.gateway.filter;

import com.football.gateway.utils.AuthJwtProperties;
import com.football.gateway.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginGlobalFilter implements GlobalFilter, Ordered {
    private final AuthJwtProperties authJwtProperties;
    private final JwtUtils jwtUtils;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();  // 判断路径

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取request
        ServerHttpRequest request = exchange.getRequest();
        // 根据路径判断是否需要做登录验证
        if (pathExclude(request.getPath().toString())) {
            // 路径在无需登录的请求路径范围内
            // 放行
            return chain.filter(exchange);  // 把上下文传给下一个过滤器
        }
        // 获取请求头中的token
        List<String> header = request.getHeaders().get("token");// return list
        String token = null;
        if (header != null && !header.isEmpty()) {
            // 请求头内有token 开始解析
            token = header.get(0);
        }
        Long userId = null;
        try {
            userId = jwtUtils.parseJwt(token);
        } catch (Exception e) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        log.info("login global filter userId: " + userId);
        // TODO 传递用户信息

        return chain.filter(exchange);  // 放行
    }

    private boolean pathExclude(String path) {
        for (String excludePath : authJwtProperties.getExcludePaths()) {
            if (antPathMatcher.match(excludePath, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
