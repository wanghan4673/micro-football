package com.football.player.mapper;

import com.football.player.model.PlayerDetailInfo;
import com.football.player.model.PlayerSimpleInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlayerMapper {
    @Insert("""
    INSERT INTO player (id, name, league,firstName, lastName, age, height, weight, country, photo, birth,updateTime)
    VALUES (#{id}, #{name}, #{league}, #{firstName}, #{lastName}, #{age}, #{height}, #{weight}, #{country}, #{photo}, #{birth},NOW())
    ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    league = VALUES(league),
    firstName = VALUES(firstName),
    lastName = VALUES(lastName),
    age = VALUES(age),
    height = VALUES(height),
    weight = VALUES(weight),
    country = VALUES(country),
    photo = VALUES(photo),
    birth = VALUES(birth),
    updateTime = NOW();
    """)
    void updateplayer(PlayerSimpleInfo player);

    @Insert("""
    INSERT INTO seasonInfo (playerid, seasonNum, teamName, leagueName, leagueLogo, position, goals, assists, passes, tackles, yellow, red, updateTime)
    VALUES (#{playerid}, #{detailInfo.seasonNum}, #{detailInfo.teamName}, #{detailInfo.leagueName}, #{detailInfo.leagueLogo}, #{detailInfo.position}, #{detailInfo.goals}, #{detailInfo.assists}, #{detailInfo.passes}, #{detailInfo.tackles}, #{detailInfo.yellow}, #{detailInfo.red}, NOW())
    ON DUPLICATE KEY UPDATE
    playerid = VALUES(playerid),
    seasonNum = VALUES(seasonNum),
    teamName = VALUES(teamName),
    leagueName = VALUES(leagueName),
    leagueLogo = VALUES(leagueLogo),
    position = VALUES(position),
    goals = VALUES(goals),
    assists = VALUES(assists),
    passes = VALUES(passes),
    tackles = VALUES(tackles),
    yellow = VALUES(yellow),
    red = VALUES(red),
    updateTime = NOW();
""")
    void updateplayerDetail(Integer playerid, PlayerDetailInfo.SeasonInfo detailInfo);

    @Select("""
    SELECT * FROM seasonInfo
    WHERE playerid = #{playerid}
""")
    List<PlayerDetailInfo.SeasonInfo> getSeasonInfo(Integer playerid);

    @Select("""
    SELECT * FROM player
    WHERE id = #{playerid}
""")
    PlayerSimpleInfo getPlayer(Integer playerid);

    @Select("""
        <script>
        SELECT count(*) FROM player
        <where>
        <if test="league != null and league != '' and league!='全部赛事'">
                AND league LIKE CONCAT('%', #{league}, '%')
            </if>
        </where>
        </script>
       """)
    Long count(String league);

    @Select("""
        <script>
        SELECT id, name, league, firstName, lastName, age, height, weight, country, photo
        FROM player
        <where>
            <if test="league != null and league != '' and league!='全部赛事'">
                AND league LIKE CONCAT('%', #{league}, '%')
            </if>
        </where>
        LIMIT #{start},#{size}
        </script>
    """)
    List<PlayerSimpleInfo> selectPlayers(Integer start,Integer size,String league);

}
