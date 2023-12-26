package com.football.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    String content;
    Integer postid;
     Timestamp time;
     String reply;
     String status;
     Integer userid;
    Integer reporterid;
}
