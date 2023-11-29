package com.football.user.model;

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
    String password;  // 密码
}
