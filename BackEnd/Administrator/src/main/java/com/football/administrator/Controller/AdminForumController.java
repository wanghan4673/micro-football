package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.AdminForumService;
import com.football.administrator.model.Result;
import com.football.mfapi.client.AdminForumClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/forum")
public class AdminForumController {
    @Autowired
    AdminForumService adminForumService;
    @Autowired
    AdminForumClient adminForumClient;

    @PostMapping("/postReport")
    public Result postReport(
            @RequestParam("reporterName") String reporterName,
            @RequestParam("reason") String reason,
            @RequestParam("postId") Integer postId
    ){
        adminForumService.postReport(reporterName,reason,postId);
        return Result.success();
    }

    @GetMapping("/getReportList")
    public Result getReportList(){
        return Result.success(adminForumService.getReportList());
    }

    @DeleteMapping("/deleteReport")
    public Integer deleteReport(@RequestParam("id") Integer id){
        adminForumService.deleteReport(id);
        return id;
    }

    @PostMapping("/test")
    public String test(@RequestParam("reporterName") String reporterName,
                       @RequestParam("reason") String reason,
                       @RequestParam("postId") Integer postId){
        adminForumClient.postReport(reporterName,reason,postId);
        return "成功";
    }
}
