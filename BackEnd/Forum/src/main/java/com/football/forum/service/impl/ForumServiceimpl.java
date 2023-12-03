package com.football.forum.service.impl;

import com.football.forum.mapper.ForumMapper;
import com.football.forum.model.Post;
import com.football.forum.service.intf.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceimpl implements ForumService {
    @Autowired
    private ForumMapper forumMapper;

    @Override
    public String test(){
        return "forumMapper.test()";
    }
}