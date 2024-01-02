package com.football.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    Integer id;  // 帖子id
    String time;  // 发布时间
    String content;  // 帖子内容
    Integer likes;  // 点赞数
    Integer comments; //评论数
    Integer collect;  // 收藏数
    String title;  // 标题
    String tags; //tag
    Long userid; //用户id
    String[] img;//图片
}


