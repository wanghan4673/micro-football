package com.football.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    Integer userid;
    Integer postid;
    Timestamp time;
    String comment;
}
