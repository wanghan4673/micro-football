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
        log.info("----------login global filter----------");
        // 获取request
        ServerHttpRequest request = exchange.getRequest();
        // 根据路径判断是否需要做登录验证
        if (pathExclude(request.getPath().toString())) {
            // 路径在无需登录的请求路径范围内
            // 放行
            log.info("the path doesn't need token");
            return chain.filter(exchange);  // 把上下文传给下一个过滤器
        }
        // 获取请求头中的token
        List<String> header = request.getHeaders().get("token");// return list
        if(pathBothJudge(request.getPath().toString())){
            // 路径在登录/不登录均需要处理的路径范围内(这个范围内的请求没有token也可以 有token也可以)
            if(header == null || header.isEmpty() || header.get(0).isEmpty()){
                // 未登录 可放行
                log.info("not login");
                return chain.filter(exchange);
            }
        }
        String token = null;
        if (header != null && !header.isEmpty()) {
            // 请求头内有token 开始解析
            token = header.get(0);
        }
        log.info("token is "+token);
        Long userId;
        try {
            userId = jwtUtils.parseJwt(token);
        } catch (Exception e) {
            if(pathBothJudge(request.getPath().toString())){
                log.info("this path may have expired token, but can be treated as not logged in.");
                return chain.filter(exchange);
            }
            log.info("in");
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        log.info("login global filter userId: " + userId);
        String user_token_id = userId.toString();
        // 传递id
        ServerWebExchange serverWebExchange = exchange.mutate()
                .request(builder -> builder.header("user-token-id", user_token_id))
                .build();
        return chain.filter(serverWebExchange);  // 放行
    }

    private boolean pathExclude(String path) {
        for (String excludePath : authJwtProperties.getExcludePaths()) {
            if (antPathMatcher.match(excludePath, path)) {
                return true;
            }
        }
        return false;
    }

    private boolean pathBothJudge(String path){
        for (String excludePath : authJwtProperties.getBothPaths()) {
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
