package com.football.forum.service.impl;

import com.football.common.utils.UserContext;
import com.football.forum.mapper.ForumMapper;
import com.football.forum.model.*;
import com.football.forum.service.intf.ForumService;
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
        return new PostInfo(post,commentInfos,user,isliked,iscollect);
    }

    @Override
    public void newpost(Post post){
        Long userid = UserContext.getUser();
        forumMapper.newPost(post,userid);
    }

    @Override
    public void likepost(Long postid){
        Long userid = UserContext.getUser();
        if(forumMapper.checklike(postid,userid)){
            forumMapper.removelike(postid,userid);
        }else {
            forumMapper.likePost(postid, userid);
        }
    }

    @Override
    public void collectpost(Long postid){
        Long userid = UserContext.getUser();
        if(forumMapper.checkcollect(postid,userid)){
            forumMapper.removecollect(postid,userid);
        }else {
            forumMapper.collectPost(postid, userid);
        }
    }
    @Override
    public void comment(Integer postid,String comment){
        Long userid = UserContext.getUser();
        forumMapper.insertComment(postid,userid,comment);
    }

    @Override
    public void follow(Long followerid){
        Long userid = UserContext.getUser();
        if(forumMapper.checkcfollow(followerid,userid)){
            forumMapper.removefollow(followerid,userid);
        }else {
            forumMapper.follow(followerid, userid);
        }
    }
}