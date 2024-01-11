package com.football.mfapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedNewsDTO {
    Long id;
    String content;  // 举报原因文本
    Timestamp reportTime;
    Long reporterId;
    Long newsId;
    String reporterName;
}
