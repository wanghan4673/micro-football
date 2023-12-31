package com.football.mfapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("administrator-service")
public interface SystemClient {
    @PostMapping("/admin/sysInfo/postNewUser")
    void postNewUser();

    @PostMapping("/admin/sysInfo/postNewPost")
    void postNewPost();
}
