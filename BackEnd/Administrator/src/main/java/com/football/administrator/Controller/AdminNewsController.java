package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.SystemInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/news")
@Slf4j
public class AdminNewsController {
    @Autowired
    SystemInfoService systemInfoService;
    @PostMapping("/news")
    public void postNews(@RequestParam("contains") String contains){
        systemInfoService.postNewNews();
        System.out.println(contains);
    }
}
