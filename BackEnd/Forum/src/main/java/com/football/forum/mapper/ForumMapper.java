package com.football.forum.mapper;

import com.football.forum.model.Post;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ForumMapper {
    @Select("""
        <script>
        SELECT count(*) FROM post
        <where>
        <if test="keyword != null and keyword != ''">
        (title LIKE CONCAT('%', #{keyword}, '%') OR
            content LIKE CONCAT('%', #{keyword}, '%') OR
            useraccount LIKE CONCAT('%', #{keyword}, '%'))
        </if>
        </where>
        </script>
        """)
    Long count(@Param("keyword") String keyword);


    @Select("""
        <script>
        SELECT * FROM post
        <where>
        <if test="keyword != null and keyword != ''">
                (title LIKE CONCAT('%', #{keyword}, '%') OR
                content LIKE CONCAT('%', #{keyword}, '%') OR
                useraccount LIKE CONCAT('%', #{keyword}, '%'))
        </if>
        </where>
        LIMIT #{start},#{size}
        </script>
    """)
    List<Post> getPosts(@Param("start") Integer start, @Param("size") Integer size, @Param("keyword") String keyword);
}

//<script>
//            SELECT * FROM post
//<where>
//<if test="keyword != null and keyword != ''">
//        (title LIKE CONCAT('%', #{keyword}, '%') OR
//        content LIKE CONCAT('%', #{keyword}, '%') OR
//        useraccount LIKE CONCAT('%', #{keyword}, '%'))
//</if>
//</where>
//</script>