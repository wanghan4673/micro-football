package com.football.mfapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDTO {
    private Integer id;
    private String detail;
    private String title;
    private LocalDateTime time;
    private Long receiver;
}
