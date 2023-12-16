package com.football.administrator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfo {
    private Integer id;
    private Integer newUserNum;
    private Integer newPostNum;
    private Integer newNewsNum;
    private Integer newAnnounceNum;
    private LocalDateTime dateTime;
}
