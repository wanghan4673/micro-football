package com.football.news.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    Long id;  // 新闻id
    String title;  // 新闻标题
    String content;  // 新闻内容
    String tags;  // 新闻标签
    Timestamp createTime;  // 发布时间
    Boolean hasPic;  // 是否有图片
    Boolean hasVideo;  // 是否有视频
}
