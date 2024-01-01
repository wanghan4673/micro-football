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
}