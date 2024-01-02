package com.football.forum.controller;

import com.football.forum.model.*;
import com.football.forum.service.intf.ForumService;
import com.football.mfapi.client.AdminForumClient;
import com.football.mfapi.client.ForumClient;
import com.football.mfapi.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
                           @RequestParam(defaultValue = "",required = false) String tag,
                           @RequestParam(defaultValue = "",required = false) String league
    ) {
        return Result.success(forumService.getPosts(page,size,keyword,tag,league));
    }

    @PostMapping("/posts")
    public Result newPost(@RequestBody Post post){
        return Result.success(forumService.newpost(post));
    }

    @PostMapping("/posts/like")
    public Result likePost(@RequestParam("postid") Long postid){
        forumService.likepost(postid);
        return Result.success();
    }

    @PostMapping("/posts/collect")
    public Result collectPost(@RequestParam("postid") Long postid){
        forumService.collectpost(postid);
        return Result.success();
    }

    @PostMapping("/posts/follow")
    public Result follow(@RequestParam() Long followerid){
        forumService.follow(followerid);
        return Result.success();
    }

    @PostMapping("/posts/comment")
    public Result comment(@RequestBody() Comment comment){
        forumService.comment(comment.getPostid(),comment.getComment());
        return Result.success();
    }

    @GetMapping("/posts/{id}")
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

    @PostMapping("/postimgs")
    public Result upload(@RequestParam("postid") Integer postid,
                         MultipartFile file) throws IOException {
        String filename = forumService.uploadFile(postid,file);
        return Result.success(filename);
    }
}
