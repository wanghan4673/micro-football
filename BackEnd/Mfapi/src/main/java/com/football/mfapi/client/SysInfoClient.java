package com.football.mfapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("administrator-service")
public interface SysInfoClient {
    @PostMapping("/admin/sysInfo/postNewUser")
    void postNewUser();

//    @PostMapping("")
}
