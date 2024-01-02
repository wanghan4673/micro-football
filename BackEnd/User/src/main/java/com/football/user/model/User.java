package com.football.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Long id;  // 用户id
    String name;  // 用户名
    String account;  // 账号
    String password;  // 密码
    String signature;  // 签名
    Integer score;  // 积分
    Integer follow;  // 关注人数
    Integer fans;  // 粉丝数
    String avatar;  // 图片路径
    Boolean isbanned;  // 是否被封禁
    String favoriteLeague;  // 关注的赛事
}
