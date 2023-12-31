package com.football.mfapi.client;

import com.football.mfapi.dto.AnnouncementDTO;
import com.football.mfapi.dto.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("forum-service")
public interface ForumClient {
    @GetMapping("/forum/posts")
    List<PostDTO> getAllPost();

    @GetMapping("/forum/post")
    PostDTO getPostForAdmin(@RequestParam("id") Integer postid);

    @DeleteMapping("/forum/post")
    Boolean deletePost(@RequestParam("id") Integer postid);
}
