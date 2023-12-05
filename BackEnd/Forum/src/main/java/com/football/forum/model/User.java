package com.football.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Integer id;  // 用户id
    String name;  // 用户名
    String account;  // 账号
    Integer follow;  // 关注人数
    Integer fans;  // 粉丝数
    String signature;
    String avatar;
}

