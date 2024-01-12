package com.football.player.mapper;

import com.football.player.model.GameDetailInfo;
import com.football.player.model.GameSimpleInfo;
import com.football.player.model.PlayerSimpleInfo;
import com.football.player.model.TeamDetailInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GameMapper {
    @Insert("""
    INSERT INTO game (date, status, venueName, venueCity, homeTeamId, homeTeamName, homeTeamLogo, awayTeamId, awayTeamName, awayTeamLogo, homeGoal, awayGoal, leagueName, updateTime)
    VALUES (#{game.date}, #{game.status}, #{game.venueName}, #{game.venueCity}, #{game.homeTeamId}, #{game.homeTeamName}, #{game.homeTeamLogo}, #{game.awayTeamId}, #{game.awayTeamName}, #{game.awayTeamLogo}, #{game.homeGoal}, #{game.awayGoal}, #{leagueName}, NOW())
    ON DUPLICATE KEY UPDATE
    date = VALUES(date),
    status = VALUES(status),
    venueName = VALUES(venueName),
    venueCity = VALUES(venueCity),
    homeTeamId = VALUES(homeTeamId),
    homeTeamName = VALUES(homeTeamName),
    homeTeamLogo = VALUES(homeTeamLogo),
    awayTeamId = VALUES(awayTeamId),
    awayTeamName = VALUES(awayTeamName),
    awayTeamLogo = VALUES(awayTeamLogo),
    homeGoal = VALUES(homeGoal),
    awayGoal = VALUES(awayGoal),
    leagueName = VALUES(leagueName),
    updateTime = NOW();
    """)
    void updateGame(GameSimpleInfo game, String leagueName);

    @Insert("""
    INSERT INTO game (date, status, venueName, venueCity, homeTeamId, homeTeamName, homeTeamLogo, awayTeamId, awayTeamName, awayTeamLogo, homeGoal, awayGoal, leagueName,leagueLogo, updateTime)
    VALUES (#{game.date}, #{game.status}, #{game.venueName}, #{game.venueCity}, #{game.homeTeamId}, #{game.homeTeamName}, #{game.homeTeamLogo}, #{game.awayTeamId}, #{game.awayTeamName}, #{game.awayTeamLogo}, #{game.homeGoal}, #{game.awayGoal}, #{game.leagueName}, #{game.leagueLogo},NOW())
    ON DUPLICATE KEY UPDATE
    date = VALUES(date),
    status = VALUES(status),
    venueName = VALUES(venueName),
    venueCity = VALUES(venueCity),
    homeTeamId = VALUES(homeTeamId),
    homeTeamName = VALUES(homeTeamName),
    homeTeamLogo = VALUES(homeTeamLogo),
    awayTeamId = VALUES(awayTeamId),
    awayTeamName = VALUES(awayTeamName),
    awayTeamLogo = VALUES(awayTeamLogo),
    homeGoal = VALUES(homeGoal),
    awayGoal = VALUES(awayGoal),
    leagueName = VALUES(leagueName),
    leagueLogo = VALUES(leagueLogo),
    updateTime = NOW();
    """)
    void updateGameDetail(GameDetailInfo game);

    @Select("""
        <script>
        SELECT count(*) FROM game
        <where>
        <if test="league != null and league != '' and league!='全部赛事'">
                AND leagueName LIKE CONCAT('%', #{league}, '%')
            </if>
        </where>
        </script>
       """)
    Long count(String league);

    @Select("""
        <script>
        SELECT id, date, status, venueName, venueCity, homeTeamId, homeTeamName, homeTeamLogo, homeGoal,awayTeamId,awayTeamName,awayTeamLogo, awayGoal,leagueName
        FROM game
        <where>
            <if test="league != null and league != '' and league!='全部赛事'">
                AND leagueName LIKE CONCAT('%', #{league}, '%')
            </if>
        </where>
        LIMIT #{start},#{size}
        </script>
    """)
    List<GameSimpleInfo> selectGames(Integer start, Integer size, String league);

    @Select("""
    SELECT * FROM game
    WHERE id = #{gameid}
""")
    GameDetailInfo getgameDetail(Integer gameid);
}
