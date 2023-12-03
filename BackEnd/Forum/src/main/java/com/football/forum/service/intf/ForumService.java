package com.football.forum.service.intf;

import com.football.forum.model.Post;
import com.football.forum.model.Posts;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ForumService {
    Posts getPosts(int page, int size, String keyword);
}
