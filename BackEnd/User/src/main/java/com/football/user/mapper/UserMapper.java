package com.football.user.mapper;

import com.football.user.model.AdminUsers;
import com.football.user.model.GameSubscription;
import com.football.user.model.MyPost;
import com.football.user.model.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("select * from user where account = #{account} and password = #{password}")
    User getUserByAccAndPas(User user);

    @Select("select name,signature,score,favorite_league from user where _id = #{userId}")
    User getUserInfo(Long userId);

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

    @Select("select name,avatar from user where _id=#{userId}")
    User getNameAndAvatar(Long userId);

    @Insert("INSERT INTO user(name,account,password) VALUES (#{name},#{account},#{password})")
    void insertUser(String name, String account, String password);

    @Select("select _id,title,content,likes,time,collect from post where userid = #{userId}")
    List<MyPost> getMyPosts(Long userId);

    @Select("select followerid from follow where fansid = #{userId}")
    List<Integer> getFollowerIds(Long userId);

    @Select("""
                <script>
                select _id,name,signature from user where _id in 
                <foreach item='id' collection='followerIds' open='(' separator=',' close=')'>
                #{id}
                </foreach>
                </script>
            """)
    List<User> getFollowersByIds(List<Integer> followerIds);

    @Select("select fansid from follow where followerid = #{userId}")
    List<Integer> getFansIds(Long userId);

    @Select("""
                <script>
                select _id,name,signature from user where _id in 
                <foreach item='id' collection='fansIds' open='(' separator=',' close=')'>
                #{id}
                </foreach>
                </script>
            """
    )
    List<User> getFansByIds(List<Integer> fansIds);

    @Select("select account from user where _id = #{userId} and password = #{oriPassword}")
    String getUserByIdAndPs(Long userId, String oriPassword);

    @Update("UPDATE user SET password = #{newPassword} WHERE _id=#{userId}")
    boolean updatePassword(Long userId, String newPassword);

    @Delete("DELETE FROM follow WHERE fansid = #{userId} AND followerid = #{deleteId}")
    boolean deleteFollow(Long userId, Long deleteId);

    @Update("UPDATE user SET follow = follow - 1 WHERE _id = #{userId}")
    boolean minusUserFollowNum(Long userId);

    @Select("select _id,name,score,follow,fans,createdate,isbanned from user")
    List<AdminUsers> getAllUsers();

    @Select("select _id,name,score,follow,fans,createdate,isbanned from user where isbanned = 1")
    List<AdminUsers> getBannedUsers();

    @Update("UPDATE user SET isbanned = CASE WHEN isbanned = 1 THEN 0 ELSE 1 END WHERE _id = #{id}")
    boolean updateBanStatus(Long id);

    @Update("UPDATE user SET favorite_league = #{league} WHERE _id = #{userId}")
    boolean updateLeague(String league, Long userId);

    @Insert("INSERT INTO gamebooking(userid,gameid,starttime) VALUES (#{userId},#{gameId},#{startTime})")
    boolean insertGameBook(Long userId, Long gameId, String startTime);

    @Select("SELECT * FROM gamebooking WHERE userid = #{userId} AND STR_TO_DATE(starttime, '%Y-%m-%dT%H:%i:%s') BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 2 DAY)")
    List<GameSubscription> getGamesByUserId(Long userId);
}
