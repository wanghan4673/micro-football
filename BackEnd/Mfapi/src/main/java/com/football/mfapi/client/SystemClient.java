package com.football.mfapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("administrator-service")
public interface SystemClient {
    @PostMapping("/admin/sys/new-user")
    void postNewUser();

    @PostMapping("/admin/sys/new-post")
    void postNewPost();
}
