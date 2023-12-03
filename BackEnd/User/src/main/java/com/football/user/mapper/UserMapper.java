package com.football.user.mapper;

import com.football.user.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("select * from user where account = #{account} and password = #{password}")
    User getUserByAccAndPas(User user);

    @Select("select score from user where _id = #{userId}")
    Integer getScore(Long userId);

    @Select("select follow,fans from user where _id = #{userId}")
    List<Map<String, Integer>> getFollowCount(Long userId);

    @Update("UPDATE user SET name=#{name},signature=#{signature}, email = #{email} WHERE _id = #{userId}")
    boolean updateUser(Long userId, String name, String signature, String email);

    @Update("UPDATE user SET score = score + 5 WHERE _id=#{userId}")
    boolean checkIn(Long userId);

    @Insert("INSERT INTO attendance(userid) Values (#{userId})")
    boolean addCheckDay(Long userId);

    @Select("select time from attendance where userid=#{userId}")
    List<Timestamp> getCheckTimes(Long userId);
}
