package com.football.news.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "newshavepicture", schema = "football_news", catalog = "")
@IdClass(NewshavepictureEntityPK.class)
public class NewshavepictureEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "news_id")
    private int newsId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pictureroute")
    private String pictureroute;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getPictureroute() {
        return pictureroute;
    }

    public void setPictureroute(String pictureroute) {
        this.pictureroute = pictureroute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewshavepictureEntity that = (NewshavepictureEntity) o;
        return newsId == that.newsId && Objects.equals(pictureroute, that.pictureroute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsId, pictureroute);
    }
}
