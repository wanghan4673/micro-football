package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.AnnouncementService;
import com.football.administrator.model.Announcement;
import com.football.administrator.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AnnouncementController {
    @Autowired
    AnnouncementService announcementservice;

    @PostMapping("/postAnnouncement")
    public Result postAnnouncement(@RequestParam("title") String title,@RequestParam("detail") String detail){
        announcementservice.postAnnouncement(title,detail);
        return Result.success();
    }

    @GetMapping("/getAnnouncementList")
    public Result getAnnouncementList(){
        return Result.success(announcementservice.getAnnouncementList());
    }
}
