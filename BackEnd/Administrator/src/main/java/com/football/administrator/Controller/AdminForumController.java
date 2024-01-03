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

    @PostMapping("/report")
    public Result postReport(
            @RequestParam("reporterName") String reporterName,
            @RequestParam("reason") String reason,
            @RequestParam("postId") Integer postId
    ){
        adminForumService.postReport(reporterName,reason,postId);
        return Result.success();
    }

    @GetMapping("/reports")
    public Result getReportList(){
        return Result.success(adminForumService.getReportList());
    }

    @DeleteMapping("/report/delete/{id}")
    public Result deleteReport(@PathVariable("id") Integer id){
        adminForumService.deleteReport(id);
        return Result.success();
    }

    @PostMapping("/report/confirm")
    public Result confirmReport(@RequestParam("post_id") Integer post_id,@RequestParam("id") Integer id){
        adminForumService.deleteReport(id);
        //此处调用论坛
        return Result.success(forumClient.deletePost(post_id));
    }

    @GetMapping("/posts")
    public Result getAllPost(){
        return Result.success(forumClient.getAllPost());
    }

    @GetMapping("/post/{id}")
    public Result getSinglePost(@PathVariable("id") Integer id){
        return Result.success(forumClient.getPostForAdmin(id));
    }
}
