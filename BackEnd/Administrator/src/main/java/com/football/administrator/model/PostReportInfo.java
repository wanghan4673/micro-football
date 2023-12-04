package com.football.administrator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReportInfo {
    private Integer id;
    private String reason;
    private LocalDateTime time;
    private Integer reportUserId;
    private String reportUserName;
    private Integer reportedPostId;
    private String reportedPostName;
}
