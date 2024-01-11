package com.football.player.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamSimpleInfo {
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
}

