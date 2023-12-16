package com.football.forum.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.football.common.utils.UserContext;
import com.football.forum.mapper.ForumMapper;
import com.football.forum.model.*;
import com.football.forum.service.intf.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ForumServiceimpl implements ForumService {
    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private ElasticsearchClient client;

    @Override
    public Posts getPosts(int page, int size, String keyword, String tag) {
//        PageHelper.startPage(1,2);
//        Long count = forumMapper.count(keyword,tag);
//        Integer start = (page-1)* size;
//        if(start<0) start=0;
//        List<Post> posts = forumMapper.getPosts(start,size,keyword,timeQ,tag);
        boolean hasTag = tag != null && !tag.isEmpty() && !tag.equals("全部赛事");
        SearchResponse<Post> response;
        try {
            Query byTag = MatchQuery.of(m -> m
                    .field("tags")
                    .query(tag)
            )._toQuery();
            Query byKeyword = MatchQuery.of(m -> m
                    .field("all")
                    .query(keyword)
            )._toQuery();
            if (keyword == null || keyword.isEmpty()) {
                if (!hasTag) {
                    response = client.search(
                            e -> e.index("footballpost")
                                    .query(q -> q.matchAll(m -> m))
                                    .from((page - 1) * size)
                                    .size(size), Post.class);
                } else {
                    response = client.search(
                            e -> e.index("footballpost")
                                    .query(q -> q.bool(b -> b.must(byTag)))
                                    .from((page - 1) * size)
                                    .size(size), Post.class);
                }
            } else {
                if(!hasTag){
                    response = client.search(
                            e -> e.index("footballpost")
                                    .query(q -> q.bool(b -> b.must(byKeyword)))
                                    .highlight(highlightBuilder -> highlightBuilder
                                            .preTags("<font color='#fc5531'>")
                                            .postTags("</font>")
                                            .requireFieldMatch(false)
                                            .fields("title", highlightFieldBuilder -> highlightFieldBuilder)
                                            .fields("content", highlightFieldBuilder -> highlightFieldBuilder))
                                    .from((page - 1) * size)
                                    .size(size), Post.class);
                } else{
                    response = client.search(
                            e -> e.index("footballpost")
                                    .query(q -> q.bool(b -> b.must(byTag).must(byKeyword)))
                                    .highlight(highlightBuilder -> highlightBuilder
                                            .preTags("<font color='#fc5531'>")
                                            .postTags("</font>")
                                            .requireFieldMatch(false)
                                            .fields("title", highlightFieldBuilder -> highlightFieldBuilder)
                                            .fields("content", highlightFieldBuilder -> highlightFieldBuilder))
                                    .from((page - 1) * size)
                                    .size(size), Post.class);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return handleResponse(response);
    }

    @Override
    public PostInfo getPost(Integer postid) {
        Long userid = UserContext.getUser();
        System.out.println(userid);
        Post post = forumMapper.getPost(postid);
        List<CommentInfo> commentInfos = forumMapper.getComments(postid);
        User user = forumMapper.getPoster(postid);
        Boolean isliked = forumMapper.getiflike(postid, userid);
        Boolean iscollect = forumMapper.getifcollect(postid, userid);
        System.out.println(isliked);
        System.out.println(iscollect);
        return new PostInfo(post, commentInfos, user, isliked, iscollect);
    }

    @Override
    public void newpost(Post post) {
        Long userid = UserContext.getUser();
        try {
            Integer lastId = forumMapper.getMaxId();
            post.setId(lastId);
            post.setUserid(userid);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTime = sdf.format(currentTime);
            post.setTime(formattedTime);
            CreateResponse response = client.create(e -> e.index("footballpost")
                    .id(String.valueOf(lastId))
                    .document(post)
            );
        } catch (Exception e) {
            log.error("Exception during importQues: {}", e.getMessage(), e);
        }
        forumMapper.newPost(post,userid);
    }

    @Override
    public void likepost(Long postid) {
        Long userid = UserContext.getUser();
        if (forumMapper.checklike(postid, userid)) {
            forumMapper.removelike(postid, userid);
        } else {
            forumMapper.likePost(postid, userid);
        }
    }

    @Override
    public void collectpost(Long postid) {
        Long userid = UserContext.getUser();
        if (forumMapper.checkcollect(postid, userid)) {
            forumMapper.removecollect(postid, userid);
        } else {
            forumMapper.collectPost(postid, userid);
        }
    }

    @Override
    public void comment(Integer postid, String comment) {
        Long userid = UserContext.getUser();
        forumMapper.insertComment(postid, userid, comment);
    }

    @Override
    public void follow(Long followerid) {
        Long userid = UserContext.getUser();
        if (forumMapper.checkcfollow(followerid, userid)) {
            forumMapper.removefollow(followerid, userid);
        } else {
            forumMapper.follow(followerid, userid);
        }
    }

    private Posts handleResponse(SearchResponse<Post> response){
        log.info("----------------------entry es response------------------------");
        HitsMetadata<Post> hits = response.hits();
        long total = hits.total().value();  // 总条数
        List<Post> posts = new ArrayList<>();
        for (Hit<Post> result : hits.hits()) {
            Post post = result.source();
            Map<String, List<String>> highlightFields = result.highlight();
            if (highlightFields != null && !highlightFields.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : highlightFields.entrySet()) {
                    String fieldName = entry.getKey();
                    List<String> highlightValues = entry.getValue();
                    String highlightValue = highlightValues.get(0);
                    // 根据高亮字段替换source字段的值
                    switch (fieldName) {
                        case "title":
                            post.setTitle(highlightValue);
                            break;
                        case "content":
                            post.setContent(highlightValue);
                            break;
                        default:
                            break;
                    }
                }
            }
            posts.add(post);
        }
        return new Posts(total, posts);
    }
}