package com.football.forum.service.impl;

import com.football.forum.mapper.ForumMapper;
import com.football.forum.model.Post;
import com.football.forum.model.Posts;
import com.football.forum.service.intf.ForumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceimpl implements ForumService {
    @Autowired
    private ForumMapper forumMapper;

    @Override
    public Posts getPosts(int page, int size, String keyword){
//        PageHelper.startPage(1,2);
        Long count = forumMapper.count(keyword);
        Integer start = (page-1)* size;
        List<Post> posts = forumMapper.getPosts(start,size,keyword);
        return new Posts(count,posts);
    }
}