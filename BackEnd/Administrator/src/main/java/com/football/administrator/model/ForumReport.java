package com.football.administrator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumReport {
    private Integer id;
    private Integer postId;
    private String reason;
    private LocalDateTime time;
    private String reporterName;
}
