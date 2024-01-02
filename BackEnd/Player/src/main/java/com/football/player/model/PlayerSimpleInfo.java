package com.football.player.model;

import lombok.Getter;
import lombok.Setter;


/**
 * 球员的基本信息
 */
@Getter
@Setter
public class PlayerSimpleInfo {
    private Integer id;
    private String name;
    private String firstName;
    private String lastName;
    private Integer age;
    private String height;
    private String weight;
    private String country;
    private String photo;
}
