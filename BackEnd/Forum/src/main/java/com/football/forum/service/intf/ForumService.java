package com.football.forum.service.intf;

import com.football.forum.model.*;
import com.football.mfapi.dto.PostDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
public interface ForumService {
    Posts getPosts(int page, int size, String keyword,String tag);
    PostInfo getPost(Integer postid);

    Post newpost(Post post);

    void likepost(Long postid);

    void collectpost(Long followerid);

    void follow(Long followerid);

    void comment(Integer postid,String comment);

    void newpostimg(Integer postid,String url);
    String uploadFile(Integer postid, MultipartFile file) throws IOException;
}
