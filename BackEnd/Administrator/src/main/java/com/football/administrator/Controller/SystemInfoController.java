package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.SystemInfoService;
import com.football.administrator.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
@RestController
@RequestMapping("/admin/sysInfo")
public class SystemInfoController {
    @Autowired
    SystemInfoService systemInfoService;

    @PostMapping("/postNewUser")
    public Result postNewUser(){
        systemInfoService.postNewUser();
        return Result.success();
    }

    @PostMapping("/postNewNews")
    public Result postNewNews(){
        systemInfoService.postNewNews();
        return Result.success();
    }

    @PostMapping("/postNewPost")
    public Result postNewPost(){
        systemInfoService.postNewPost();
        return Result.success();
    }

    @PostMapping("/postNewAnnounce")
    public Result postNewAnnounce(){
        systemInfoService.postNewAnnounce();
        return Result.success();
    }
}
