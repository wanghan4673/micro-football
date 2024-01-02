package com.football.forum.controller;

import com.football.forum.service.intf.ForumAdminService;
import com.football.mfapi.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/forum")
public class ForumAdminController {
    @Autowired
    private ForumAdminService forumAdminService;

    @GetMapping("/ad-allposts")
    public List<PostDTO> GetAllPosts(){
        return forumAdminService.getAllPosts();
    }

    @GetMapping("/ad-posts")
    public PostDTO getPostForAdmin(@RequestParam("id") Integer postid){
        return forumAdminService.getPostForAdmin(postid);
    }

    @DeleteMapping("/ad-posts")
    public Boolean deletePost(@RequestParam("id") Integer postid){
        return  forumAdminService.deletePost(postid);
    }

}
