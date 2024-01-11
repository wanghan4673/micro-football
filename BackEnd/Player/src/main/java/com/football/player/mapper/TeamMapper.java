package com.football.player.mapper;

import com.football.player.model.PlayerSimpleInfo;
import com.football.player.model.TeamDetailInfo;
import com.football.player.model.TeamSimpleInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamMapper {
    @Insert("""
    INSERT INTO team (id, name, country,logo, founded, updateTime)
    VALUES (#{id}, #{name}, #{country}, #{logo}, #{founded},NOW())
    ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    country = VALUES(country),
    logo = VALUES(logo),
    founded = VALUES(founded),
    updateTime = NOW();
    """)
    void updateTeam(TeamSimpleInfo team);

    @Insert("""
    INSERT INTO team (id, name, country,logo, founded,venueName,venueAddress,venueCity,venueImage,venueCapacity, updateTime)
    VALUES (#{id}, #{name}, #{country}, #{logo}, #{founded},#{venueName}, #{venueAddress}, #{venueCity}, #{venueImage},#{venueCapacity},NOW())
    ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    country = VALUES(country),
    logo = VALUES(logo),
    founded = VALUES(founded),
    venueName = VALUES(venueName),
    venueAddress = VALUES(venueAddress),
    venueCity = VALUES(venueCity),
    venueImage = VALUES(venueImage),
    venueCapacity = VALUES(venueCapacity),
    updateTime = NOW();
    """)
    void updateTeamDetail(TeamDetailInfo team);

    @Insert("""
    INSERT INTO player (id, name, age,number, position,photo,teamid, updateTime)
    VALUES (#{player.id}, #{player.name}, #{player.age}, #{player.number}, #{player.position},#{player.photo},#{teamid},NOW())
    ON DUPLICATE KEY UPDATE
    id = VALUES(player.id),
    name = VALUES(player.name),
    age = VALUES(player.age),
    number = VALUES(player.number),
    position = VALUES(player.position),
    photo = VALUES(player.photo),
    teamid = VALUES(teamid),
    updateTime = NOW();
    """)
    void updateMember(TeamDetailInfo.PlayerInfo player,Integer teamid);

    @Select("""
        SELECT count(*) FROM team
       """)
    Long count();

    @Select("""
        <script>
        SELECT id, name, country,logo, founded
        FROM team
        LIMIT #{start},#{size}
        </script>
    """)
    List<TeamSimpleInfo> selectTeams(Integer start, Integer size);

    @Select("""
        SELECT id, name, country,logo, founded,venueName,venueAddress,venueCapacity,venueCity,venueImage
        FROM team
        WHERE id = #{id}
    """)
    TeamDetailInfo getTeam(Integer id);

    @Select("""
        SELECT id, name,age,number,position,photo
        FROM player
        WHERE teamid = #{id}
    """)
    List<TeamDetailInfo.PlayerInfo> getMember(Integer id);
}
