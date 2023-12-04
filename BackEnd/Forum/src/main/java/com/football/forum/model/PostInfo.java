package com.football.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfo {
    Post post;
    List<CommentInfo> commentInfos;
    User user;
    Boolean isliked;
    Boolean iscollected;
}
