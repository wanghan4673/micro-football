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

}
