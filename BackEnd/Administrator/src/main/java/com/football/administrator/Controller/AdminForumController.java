package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.AdminForumService;
import com.football.administrator.model.Result;
import com.football.mfapi.client.AdminForumClient;
import com.football.mfapi.client.ForumClient;
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
    ForumClient forumClient;

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

    @PostMapping("/deleteReport")
    public Result deleteReport(@RequestParam("id") Integer id){
        adminForumService.deleteReport(id);
        return Result.success();
    }

    @GetMapping("/getAllPost")
    public Result getAllPost(){
        return Result.success(forumClient.getAllPost());
    }
}
