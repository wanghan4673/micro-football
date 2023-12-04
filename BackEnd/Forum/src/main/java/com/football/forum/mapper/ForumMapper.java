package com.football.forum.mapper;

import com.football.forum.model.Comment;
import com.football.forum.model.CommentInfo;
import com.football.forum.model.Post;
import com.football.forum.model.User;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
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
        <choose>
            <when test="timeQ">
                ORDER BY time DESC
            </when>
            <otherwise>
               ORDER BY (likes * 1 + collect * 5 + comments * 3) DESC
            </otherwise>
        </choose>
        LIMIT #{start},#{size}
        </script>
    """)
    List<Post> getPosts(@Param("start") Integer start, @Param("size") Integer size, @Param("keyword") String keyword,@Param("timeQ") Boolean timeQ);

    @Select("""
        <script>
        SELECT * FROM post
        WHERE
        <if test="postid != null">
            _id = #{postid}
        </if>
        </script>
     """)
    Post getPost(@Param("postid") Integer postid);

    @Select("""
        SELECT comment.comment,comment.time,comment.userid,user.name,user.avatar
        FROM comment join user ON comment.userid = user._id
        WHERE
            postid = #{postid}
        ORDER BY comment.time
    """)
    List<CommentInfo> getComments(@Param("postid") Integer postid);

    @Select("""
        SELECT *
        FROM post join user ON post.userid = user._id
        WHERE
            post._id = #{postid}
    """)
    User getPoster(@Param("postid") Integer postid);

    @Select("""
        SELECT COUNT(*) > 0
        FROM likes
        WHERE postid = #{postid} AND userid = #{userid}
    """)
    Boolean getiflike(@Param("postid") Integer postid,@Param("userid") Long userid);

    @Select("""
        SELECT COUNT(*) > 0
        FROM collect
        WHERE postid = #{postid} AND userid = #{userid}
    """)
    Boolean getifcollect(@Param("postid") Integer postid,@Param("userid") Long userid);

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