package com.football.forum.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "tencent.cos")
public class TencentProperties {
    private String secretId;
    private String secretKey;
    private String region;
    private String cosUrl;
    private String bucket;
}
