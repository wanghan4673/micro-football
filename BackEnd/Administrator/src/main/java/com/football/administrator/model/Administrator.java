package com.football.administrator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administrator {
    private Integer id;
    private String account;
    private String password;
}
