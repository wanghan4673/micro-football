package com.football.forum.controller;

import com.football.forum.model.Post;
import com.football.forum.model.Result;
import com.football.forum.service.intf.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@Slf4j
@RequestMapping("/forum")
public class ForumController {
    @Autowired
    private ForumService forumService;
    @GetMapping()
//    public Result GetPosts(@RequestParam(required = false) String keyword,
//                           @RequestParam(defaultValue = "0") int page,
//                           @RequestParam(defaultValue = "10") int size) {
////        Pageable pageable = PageRequest.of(page, size);
////        return forumService.getPosts(pageable);
//        return null;
    public String GetPosts(){
        return "success";
    }
}
