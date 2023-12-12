package com.football.news.model;

import java.sql.Timestamp;
import java.util.List;

public class NewsEntityInfo extends NewsEntity {
    private final List<String> pictureRoutes;

    public NewsEntityInfo(Integer news_id,String title, Timestamp publishdatetime, String summary,String contains,String matchtag,String propertag, List<String> pictureRoutes) {
        // 调用父类的构造器
        super(news_id,title, publishdatetime,summary,contains,matchtag,propertag);
        // 初始化pic的属性
        this.pictureRoutes = pictureRoutes;
    }

    // 提供一个getter方法
    public List<String> getPic() {
        return pictureRoutes;
    }


}
