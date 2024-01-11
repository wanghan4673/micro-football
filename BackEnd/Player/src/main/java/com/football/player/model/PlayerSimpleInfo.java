package com.football.player.model;

import lombok.*;


/**
 * 球员的基本信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSimpleInfo {
    private Integer id;
    private String name;
    private String league;
    private String firstName;
    private String lastName;
    private Integer age;
    private String height;
    private String weight;
    private String country;
    private String photo;
    private String birth;
}
