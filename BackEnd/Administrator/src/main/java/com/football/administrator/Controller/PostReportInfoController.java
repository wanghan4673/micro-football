package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.PostReportInfoService;
import com.football.administrator.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/forum")
public class PostReportInfoController {
    @Autowired
    PostReportInfoService postreportinfoservice;

    @PostMapping("/postReport")
    public Result postReport(
            @RequestParam("reportedPostId") Integer reportedPostId,
            @RequestParam("reason") String reason,
            @RequestParam("reportUserId") Integer reportUserId
    ){
        postreportinfoservice.postReport(reportedPostId,reason,reportUserId);
        return Result.success();
    }

    @GetMapping("/getReportList")
    public Result getReportList(){
        return Result.success(postreportinfoservice.getReportList());
    }

    @DeleteMapping("/deleteReport")
    public Result deleteReport(@RequestParam("id") Integer id){
        postreportinfoservice.deleteReport(id);
        return Result.success();
    }
}
