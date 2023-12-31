package com.football.mfapi.client;

import com.football.mfapi.dto.AnnouncementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("administrator-service")
public interface AnnouncementClient {
    @GetMapping("/admin/announcement/getAnnouncementForUser")
    List<AnnouncementDTO> getAnnouncementById(@RequestParam("id") Long id);
}
