package com.football.news.model.Info;

import com.football.news.model.Entity.NewsEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsEntityInfo extends NewsEntity implements Comparable<NewsEntityInfo> {
    private final List<String> pictureRoutes;

    public NewsEntityInfo(Integer news_id,String title, Timestamp publishdatetime, String summary,String contains,String matchtag,String propertag, List<String> pictureRoutes) {
        super(news_id,title, publishdatetime,summary,contains,matchtag,propertag);
        this.pictureRoutes = pictureRoutes;
    }

    public List<String> getPic() {
        return pictureRoutes;
    }




    @Override
    public int compareTo (NewsEntityInfo other) {
        return other.relevance - this.relevance;
    }
    //关联度（用于搜索结果的排序）
    int relevance=0;
}
