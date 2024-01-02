package com.football.player.model;

import lombok.Getter;
import lombok.Setter;

import javax.print.DocFlavor;
import java.util.List;

/**
 * 球员的详细信息
 */
@Getter
@Setter
public class PlayerDetailInfo {
    private Integer id;
    private String name;
    private String firstName;
    private String lastName;
    private Integer age;
    private String height;
    private String weight;
    private String country;
    private String photo;
    private String birth;
    private List<SeasonInfo> seasonInfos;

    @Getter
    @Setter
    public static class SeasonInfo{
        String seasonNum;
        String teamName;
        String teamLogo;
        String leagueName;
        String leagueLogo;
        String position;
        Integer goals;
        Integer assists;
        Integer passes;
        Integer tackles;
        Integer yellow;
        Integer red;
    }

    public void show(){
        System.out.println("球员信息：");
        System.out.println("ID: " + id);
        System.out.println("姓名: " + name);
        System.out.println("年龄: " + age);
        System.out.println("身高: " + height );
        System.out.println("体重: " + weight);
        System.out.println("国家: " + country);
        System.out.println("生日: " + birth);
    }

}