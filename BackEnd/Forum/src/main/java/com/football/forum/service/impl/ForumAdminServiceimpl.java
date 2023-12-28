package com.football.forum.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import com.football.forum.mapper.ForumMapper;
import com.football.forum.service.intf.ForumAdminService;
import com.football.mfapi.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ForumAdminServiceimpl implements ForumAdminService {
    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private ElasticsearchClient client;

    @Override
    public List<PostDTO> getAllPosts() {
        return forumMapper.getAllPosts();
    }

    @Override
    public PostDTO getPostForAdmin(Integer postid) {
        return forumMapper.getPostDTO(postid);
    }

    @Override
    public Boolean deletePost(Integer postid) {
        try{
            DeleteResponse response = client.delete(e -> e.index("footballpost").id(String.valueOf(postid)));
            return forumMapper.deletePost(postid);
        } catch (Exception e) {
            log.error("Exception during importQues: {}", e.getMessage(), e);
            return false;
        }
    }
}
