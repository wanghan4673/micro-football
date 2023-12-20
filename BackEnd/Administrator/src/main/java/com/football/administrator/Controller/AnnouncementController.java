package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.AnnouncementService;
import com.football.administrator.model.Result;
import com.football.mfapi.client.AnnouncementClient;
import com.football.mfapi.dto.AnnouncementDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/announcement")
@RequiredArgsConstructor
public class AnnouncementController {
    @Autowired
    AnnouncementService announcementservice;
    private final AnnouncementClient announcementClient;

    @PostMapping("/postAnnouncement")
    public Result postAnnouncement(@RequestParam("title") String title,
                                   @RequestParam("detail") String detail,
                                   @RequestParam("receiver") Integer receiver){
        announcementservice.postAnnouncement(title,detail,receiver);
        return Result.success();
    }

    @GetMapping("/getAnnouncementList")
    public Result getAnnouncementList(){
        return Result.success(announcementservice.getAnnouncementList(0L));
    }

    @GetMapping("/getAnnouncementForUser")
    public List<AnnouncementDTO> getAnnouncementListForUser(@RequestParam("id") Long id){
        return announcementservice.getAnnouncementList(id);
    }


}
