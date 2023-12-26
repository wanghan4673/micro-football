package com.football.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPost {
    Integer id;  // 帖子id
    String content;  // 帖子内容
    Boolean isbanned;  // 是否封禁
    Integer likes;  // 点赞数
    Integer comments; //评论数
    Integer collect;  // 收藏数
    String title;  // 标题
    Long userid; //用户id
    String time;  // 发布时间
}


