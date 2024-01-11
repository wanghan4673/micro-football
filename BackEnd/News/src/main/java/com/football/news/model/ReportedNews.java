package com.football.news.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedNews {
    // 提供给举报人的新闻内容
    Long id;
    String content;  // 举报原因文本
    Timestamp reportTime;
    Long reporterId;
    Long newsId;
    String reporterName;
}
