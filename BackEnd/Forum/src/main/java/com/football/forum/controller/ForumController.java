package com.football.forum.controller;

import com.football.common.utils.UserContext;
import com.football.forum.model.Comment;
import com.football.forum.model.Post;
import com.football.forum.model.PostInfo;
import com.football.forum.model.Result;
import com.football.forum.service.intf.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/forum")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @GetMapping()
    public Result GetPosts(@RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(defaultValue = "10") Integer size,
                           @RequestParam(required = false) String keyword,
                           @RequestParam(defaultValue = "true",required = false) Boolean timeQ
    ) {
        return Result.success(forumService.getPosts(page,size,keyword,timeQ));
    }

    @PostMapping("/post")
    public Result newPost(@RequestBody Post post){
        forumService.newpost(post);
        return Result.success();
    }

    @PutMapping("/post/like")
    public Result likePost(@RequestParam() Long postid){
        forumService.likepost(postid);
        return Result.success();
    }

    @PutMapping("/post/collect")
    public Result collectPost(@RequestParam() Long postid){
        forumService.collectpost(postid);
        return Result.success();
    }

    @PutMapping("/post/follow")
    public Result follow(@RequestParam() Long followerid){
        forumService.follow(followerid);
        return Result.success();
    }

    @PutMapping("/post/comment")
    public Result comment(@RequestBody() Comment comment){
        forumService.comment(comment.getPostid(),comment.getComment());
        return Result.success();
    }

    @GetMapping("/post/{id}")
    public Result getPost(@PathVariable("id") Integer postid){
        PostInfo postInfo = forumService.getPost(postid);
        return Result.success(postInfo);
    }
}
