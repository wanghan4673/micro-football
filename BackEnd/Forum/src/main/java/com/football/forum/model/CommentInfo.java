package com.football.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfo {
    String comment;
    Timestamp time;
    Integer userid;
    String name;
    String signature;
    String avatar;
}
