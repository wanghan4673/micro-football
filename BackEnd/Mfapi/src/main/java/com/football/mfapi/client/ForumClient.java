package com.football.mfapi.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("forum-service")
public interface ForumClient {
}
