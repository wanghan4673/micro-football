package com.football.news.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDetail {
    //TODO:这里要完善所有内容 因为不能通过页面传值
    Long id;  // 新闻id
    String title;  // 新闻标题
    String content;  // 新闻内容
    Timestamp createTime;  // 发布时间
    String tags;  // 新闻标签
    String picUrl;  // 新闻图片url
}
