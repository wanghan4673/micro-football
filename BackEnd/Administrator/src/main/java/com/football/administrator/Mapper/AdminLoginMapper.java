package com.football.administrator.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface AdminLoginMapper {
    @Select("select id from administrator where email = #{email}")
    String getByEmail(String email);

    @Update("UPDATE administrator SET code=#{code} WHERE id=#{id}")
    boolean updateCode(String code,String id);

    @Select("select id from administrator where email=#{email} and code=#{code} and createdate >= #{appointTime}")
    String checkCode(String code, String email, LocalDateTime appointTime);
}
