package com.football.forum.service.impl;

import com.football.common.utils.UserContext;
import com.football.forum.mapper.ForumMapper;
import com.football.forum.model.*;
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
    public Posts getPosts(int page, int size, String keyword,Boolean timeQ){
//        PageHelper.startPage(1,2);
        Long count = forumMapper.count(keyword);
        Integer start = (page-1)* size;
        List<Post> posts = forumMapper.getPosts(start,size,keyword,timeQ);
        return new Posts(count,posts);
    }

    @Override
    public PostInfo getPost(Integer postid){
        Long userid = UserContext.getUser();
        System.out.println(userid);
        Post post = forumMapper.getPost(postid);
        List<CommentInfo> commentInfos =forumMapper.getComments(postid);
        User user = forumMapper.getPoster(postid);
        Boolean isliked = forumMapper.getiflike(postid,userid);
        Boolean iscollect = forumMapper.getifcollect(postid,userid);
        System.out.println(isliked);
        System.out.println(iscollect);
        PostInfo postInfo = new PostInfo(post,commentInfos,user,isliked,iscollect);
        return postInfo;
    }
}