package com.football.forum.service.intf;

import com.football.mfapi.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ForumAdminService {
    List<PostDTO> getAllPosts();
    PostDTO getPostForAdmin(Integer postid);
    Boolean deletePost(Integer postid);
}
