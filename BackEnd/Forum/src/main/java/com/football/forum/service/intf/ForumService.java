package com.football.forum.service.intf;

import com.football.forum.model.*;
import com.football.mfapi.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ForumService {
    Posts getPosts(int page, int size, String keyword,String tag);
    List<PostDTO> getAllPosts();
    PostDTO getPostForAdmin(Integer postid);
    PostInfo getPost(Integer postid);

    void newpost(Post post);

    void likepost(Long postid);

    void collectpost(Long followerid);

    void follow(Long followerid);

    void comment(Integer postid,String comment);
}
