package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.AnnouncementService;
import com.football.administrator.Service.Intf.SystemInfoService;
import com.football.administrator.model.Result;
import com.football.mfapi.client.AnnouncementClient;
import com.football.mfapi.dto.AnnouncementDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/announcement")
@RequiredArgsConstructor
public class AnnouncementController {
    @Autowired
    AnnouncementService announcementservice;
    @Autowired
    SystemInfoService systemInfoService;
    private final AnnouncementClient announcementClient;

    @PostMapping("/announcement")
    public Result postAnnouncement(@RequestParam("title") String title,
                                   @RequestParam("detail") String detail,
                                   @RequestParam("receiver") Long receiver){
        announcementservice.postAnnouncement(title,detail,receiver);
        systemInfoService.postNewAnnounce();
        return Result.success();
    }

    @GetMapping("/announcements")
    public Result getAnnouncementList(){
        return Result.success(announcementservice.getAnnouncementList(0L));
    }

    @GetMapping("/announcement-user")
    public List<AnnouncementDTO> getAnnouncementListForUser(@RequestParam("id") Long id){
        return announcementservice.getAnnouncementList(id);
    }

    @GetMapping("/test")
    public List<AnnouncementDTO> test(){
        return announcementClient.getAnnouncementById(0L);
    }
}
