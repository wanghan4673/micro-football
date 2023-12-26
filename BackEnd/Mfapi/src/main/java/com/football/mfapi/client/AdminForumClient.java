package com.football.mfapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("administrator-service")
public interface AdminForumClient {
    @PostMapping("/admin/forum/postReport")
    void postReport(@RequestParam("reporterName") String reporterName,
                    @RequestParam("reason") String reason,
                    @RequestParam("postId") Integer postId);
}
