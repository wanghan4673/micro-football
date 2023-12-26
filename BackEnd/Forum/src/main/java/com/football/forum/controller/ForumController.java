package com.football.forum.controller;

import com.football.forum.model.*;
import com.football.forum.service.intf.ForumService;
import com.football.mfapi.client.AdminForumClient;
import com.football.mfapi.client.ForumClient;
import com.football.mfapi.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/forum")
public class ForumController {
    @Autowired
    private ForumService forumService;
    @Autowired
    private AdminForumClient adminForumClient;
    @GetMapping()
    public Result GetPosts(@RequestParam(defaultValue = "1",required = false) Integer page,
                           @RequestParam(defaultValue = "10",required = false) Integer size,
                           @RequestParam(defaultValue = "",required = false) String keyword,
                           @RequestParam(defaultValue = "",required = false) String tag
    ) {
        return Result.success(forumService.getPosts(page,size,keyword,tag));
    }

    @PostMapping("/post")
    public Result newPost(@RequestBody Post post){
        forumService.newpost(post);
        return Result.success();
    }

    @PostMapping("/post/like")
    public Result likePost(@RequestParam() Long postid){
        forumService.likepost(postid);
        return Result.success();
    }

    @PostMapping("/post/collect")
    public Result collectPost(@RequestParam() Long postid){
        forumService.collectpost(postid);
        return Result.success();
    }

    @PostMapping("/post/follow")
    public Result follow(@RequestParam() Long followerid){
        forumService.follow(followerid);
        return Result.success();
    }

    @PostMapping("/post/comment")
    public Result comment(@RequestBody() Comment comment){
        forumService.comment(comment.getPostid(),comment.getComment());
        return Result.success();
    }

    @GetMapping("/post/{id}")
    public Result getPost(@PathVariable("id") Integer postid){
        PostInfo postInfo = forumService.getPost(postid);
        return Result.success(postInfo);
    }

    @PostMapping("/report")
    public Result report(@RequestParam("reporterName") String reporterName,
                         @RequestParam("reason") String reason,
                         @RequestParam("postId") Integer postId){
        adminForumClient.postReport(reporterName,reason,postId);
        return  Result.success();
    }
}
