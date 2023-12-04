package com.football.gateway.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.auth")
public class AuthJwtProperties {
    private List<String> includePaths;
    private List<String> excludePaths;
    private List<String> bothPaths;
}
