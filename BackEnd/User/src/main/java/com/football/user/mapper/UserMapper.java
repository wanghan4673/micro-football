package com.football.user.mapper;

import com.football.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where account = #{account} and password = #{password}")
    User getUserByAccAndPas(User user);
}
