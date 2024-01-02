package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.SystemInfoService;
import com.football.administrator.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
@RestController
@RequestMapping("/admin/sys")
public class SystemInfoController {
    @Autowired
    SystemInfoService systemInfoService;

    @GetMapping("/sysinfo")
    public Result getSysInfo(){
        return Result.success(systemInfoService.getSysInfo());
    }

    @PostMapping("/new-user")
    public void postNewUser(){
        systemInfoService.postNewUser();
    }

    @PostMapping("/new-news")
    public Result postNewNews(){
        systemInfoService.postNewNews();
        return Result.success();
    }

    @PostMapping("/new-post")
    public Result postNewPost(){
        systemInfoService.postNewPost();
        return Result.success();
    }

    @PostMapping("/newA-anounce")
    public Result postNewAnnounce(){
        systemInfoService.postNewAnnounce();
        return Result.success();
    }
}
