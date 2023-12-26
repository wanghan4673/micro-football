package com.football.forum.service.intf;

import com.football.forum.model.*;
import org.springframework.stereotype.Service;


@Service
public interface ForumService {
    Posts getPosts(int page, int size, String keyword,String tag);

    PostInfo getPost(Integer postid);

    void newpost(Post post);

    void likepost(Long postid);

    void collectpost(Long followerid);

    void follow(Long followerid);

    void comment(Integer postid,String comment);
    ReportInfo report(Report report);
}
