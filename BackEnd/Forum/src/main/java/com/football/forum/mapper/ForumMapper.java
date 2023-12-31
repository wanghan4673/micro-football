package com.football.forum.mapper;

import com.football.forum.model.Comment;
import com.football.forum.model.CommentInfo;
import com.football.forum.model.Post;
import com.football.forum.model.User;
import com.football.mfapi.dto.PostDTO;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ForumMapper {
//    @Select("""
//        <script>
//        SELECT count(*) FROM post
//        <where>
//        <if test="keyword != null and keyword != ''">
//        (title LIKE CONCAT('%', #{keyword}, '%') OR
//            content LIKE CONCAT('%', #{keyword}, '%'))
//        </if>
//        <if test="tag != null and tag != '' and tag!='全部赛事'">
//                AND tags LIKE CONCAT('%', #{tag}, '%')
//            </if>
//        </where>
//        </script>
//        """)
//    Long count(@Param("keyword") String keyword,String tag);

//    @Select("""
//        <script>
//        SELECT * FROM post
//        <where>
//        <if test="keyword != null and keyword != ''">
//                (title LIKE CONCAT('%', #{keyword}, '%') OR
//                content LIKE CONCAT('%', #{keyword}, '%'))
//        </if>
//            <if test="tag != null and tag != '' and tag!='全部赛事'">
//                AND tags LIKE CONCAT('%', #{tag}, '%')
//            </if>
//        </where>
//        <choose>
//            <when test="timeQ">
//                ORDER BY time DESC
//            </when>
//            <otherwise>
//               ORDER BY (likes * 1 + collect * 5 + comments * 3) DESC
//            </otherwise>
//        </choose>
//        LIMIT #{start},#{size}
//        </script>
//    """)
//    List<Post> getPosts(Integer start,Integer size,String keyword,Boolean timeQ,String tag);

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
        <script>
        SELECT * FROM post
        </script>
     """)
    List<PostDTO> getAllPosts();

    @Select("""
        <script>
        SELECT * FROM post
        WHERE _id = #{postid}
        </script>
     """)
    PostDTO getPostDTO(Integer postid);

    @Select("""
        <script>
        SELECT postimg.img FROM postimg
        WHERE postid = #{postid}
        </script>
     """)
    String[] getPostImg(Integer postid);
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
        SELECT *
        FROM user
        WHERE
            _id = #{userid}
    """)
    User getUser(@Param("userid") Integer userid);

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


    @Insert("""
    INSERT INTO post (content, title, tags,userid) VALUES (#{post.content}, #{post.title}, #{post.tags},#{userid})
    """)
    void newPost(Post post,Long userid);

    @Select("""
        SELECT COUNT(*) FROM likes
        WHERE
            postid = #{postid} AND userid = #{userid}
    """)
    Boolean checklike(Long postid,Long userid);
    @Insert("""
        INSERT INTO likes (postid,userid) VALUES (#{postid},#{userid})
    """)
    void likePost(Long postid,Long userid);

    @Delete("""
        DELETE FROM likes
        WHERE
            postid = #{postid} AND userid =#{userid}
    """)
    void removelike(Long postid,Long userid);

    @Select("""
        SELECT COUNT(*) FROM collect
        WHERE
            postid = #{postid} AND userid = #{userid}
    """)
    Boolean checkcollect(Long postid,Long userid);
    @Insert("""
        INSERT INTO collect (postid,userid) VALUES (#{postid},#{userid})
    """)
    void collectPost(Long postid,Long userid);

    @Delete("""
        DELETE FROM collect
        WHERE
            postid = #{postid} AND userid =#{userid}
    """)
    void removecollect(Long postid,Long userid);

    @Select("""
        SELECT COUNT(*) FROM follow
        WHERE
            followerid = #{followerid} AND fansid = #{userid}
    """)
    Boolean checkcfollow(Long followerid,Long userid);
    @Insert("""
        INSERT INTO follow (followerid,fansid) VALUES (#{followerid},#{userid})
    """)
    void follow(Long followerid,Long userid);

    @Delete("""
        DELETE FROM follow
        WHERE
            followerid = #{followerid} AND fansid =#{userid}
    """)
    void removefollow(Long followerid,Long userid);

    @Insert("""
        INSERT INTO comment (userid,postid,comment) VALUES (#{userid},#{postid},#{comment})
    """)
    void insertComment(Integer postid,Long userid,String comment);

    @Insert("""
        INSERT INTO postimg (postid,img) VALUES (#{postid},#{url})
    """)
    void newpostimg(Integer postid,String url);

    @Select("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'post' AND TABLE_SCHEMA = DATABASE();")
    int getMaxId();

    @Delete("""
        DELETE FROM post
        WHERE
            _id = #{postid}
    """)
    Boolean deletePost(Integer postid);
}
