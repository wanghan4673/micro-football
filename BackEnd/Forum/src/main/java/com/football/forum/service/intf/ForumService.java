package com.football.forum.service.intf;

import com.football.forum.model.Post;
import com.football.forum.model.PostInfo;
import com.football.forum.model.Posts;
import org.springframework.stereotype.Service;


@Service
public interface ForumService {
    Posts getPosts(int page, int size, String keyword,Boolean timeQ);

    PostInfo getPost(Integer postid);

    void newpost(Post post);

    void likepost(Long postid);

    void collectpost(Long followerid);

    void follow(Long followerid);

    void comment(Integer postid,String comment);
}
