package com.football.mfapi.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {
    private int newsId;
    private String title;
    private Timestamp publishdatetime;
    private String summary;
}
