package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.AdminForumService;
import com.football.administrator.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/forum")
public class AdminForumController {
    @Autowired
    AdminForumService adminForumService;

    @PostMapping("/postReport")
    public Result postReport(
            @RequestParam("reportedPostId") Integer reportedPostId,
            @RequestParam("reason") String reason,
            @RequestParam("reportUserId") Integer reportUserId
    ){
        adminForumService.postReport(reportedPostId,reason,reportUserId);
        return Result.success();
    }

    @GetMapping("/getReportList")
    public Result getReportList(){
        return Result.success(adminForumService.getReportList());
    }

    @DeleteMapping("/deleteReport")
    public Integer deleteReport(@RequestParam("id") Integer id){
        return id;
    }
}
