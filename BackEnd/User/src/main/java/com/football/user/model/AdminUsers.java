package com.football.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUsers {
    Long id;  // 用户id
    String name;  // 用户名
    Integer score;  // 积分
    Integer follow;  // 关注人数
    Integer fans;  // 粉丝数
    Boolean isbanned;  // 是否被封禁
    Timestamp createDate;  // 创建时间
}
