package com.football.news.model.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "news", schema = "football_news", catalog = "")
public class NewsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "news_id")
    private int newsId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "publishdatetime")
    private Timestamp publishdatetime;
    @Basic
    @Column(name = "summary")
    private String summary;
    @Basic
    @Column(name = "contains")
    private String contains;
    @Basic
    @Column(name = "matchtag")
    private String matchtag;
    @Basic
    @Column(name = "propertag")
    private String propertag;

    public NewsEntity(Integer newsId, String title, Timestamp publishdatetime, String summary,String contains,String matchtag,String propertag){
         this.newsId=newsId;
         this.title=title;
         this.publishdatetime=publishdatetime;
         this.summary=summary;
         this.contains=contains;
         this.matchtag=matchtag;
         this.propertag=propertag;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getPublishdatetime() {
        return publishdatetime;
    }

    public void setPublishdatetime(Timestamp publishdatetime) {
        this.publishdatetime = publishdatetime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getMatchtag() {
        return matchtag;
    }

    public void setMatchtag(String matchtag) {
        this.matchtag = matchtag;
    }

    public String getPropertag() {
        return propertag;
    }

    public void setPropertag(String propertag) {
        this.propertag = propertag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsEntity that = (NewsEntity) o;
        return newsId == that.newsId && Objects.equals(title, that.title) && Objects.equals(publishdatetime, that.publishdatetime) && Objects.equals(summary, that.summary) && Objects.equals(contains, that.contains) && Objects.equals(matchtag, that.matchtag) && Objects.equals(propertag, that.propertag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsId, title, publishdatetime, summary, contains, matchtag, propertag);
    }
}
