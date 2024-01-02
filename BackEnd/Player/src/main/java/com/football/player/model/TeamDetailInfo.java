package com.football.player.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TeamDetailInfo {
    private Integer id;
    private String name;
    private String country;
    private String logo;
    private String founded;
    private String venueName;
    private String venueAddress;
    private String venueCity;
    private String venueImage;
    private Integer venueCapacity;
    private List<PlayerInfo> players;


    @Getter
    @Setter
    public static class PlayerInfo{
        Integer id;
        String name;
        Integer age;
        Integer number;
        String position;
        String photo;
    }

    public void show(){
        System.out.println("队伍信息：");
        System.out.println("ID: " + id);
        System.out.println("姓名: " + name);
        System.out.println("国家: " + country);
        System.out.println("logo: " + logo );
        System.out.println("建立时间: " + founded);
        System.out.println("场馆名: " + venueName);
        System.out.println("场馆地址: " + venueAddress);
        System.out.println("场馆容量: " + venueCapacity);
        System.out.println("场馆城市: " + venueCity);
    }
}
