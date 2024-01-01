package com.football.player.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
public class GameDetailInfo {
    private Integer id;
    private String date;
    private String status;
    private String venueName;
    private String venueCity;
    private Integer homeTeamId;
    private String homeTeamName;
    private String homeTeamLogo;
    private Integer awayTeamId;
    private String awayTeamName;
    private String awayTeamLogo;
    private Integer homeGoal;
    private Integer awayGoal;
    private String leagueName;
    private String leagueLogo;
    private String round;

    public void show() {
        System.out.println("比赛信息：");
        System.out.println("ID: " + id);
        System.out.println("日期: " + date);
        System.out.println("状态: " + status);
        System.out.println("比赛地点: " + venueName + " in " + venueCity);
        System.out.println("主队: " + homeTeamName + " (ID: " + homeTeamId + ")");
        System.out.println("客队: " + awayTeamName + " (ID: " + awayTeamId + ")");
        System.out.println("主队进球: " + homeGoal);
        System.out.println("客队进球: " + awayGoal);
        System.out.println("联赛名: " + leagueName);
        System.out.println("联赛logo: " + leagueLogo);
        System.out.println("轮次: " + round);
    }

}
