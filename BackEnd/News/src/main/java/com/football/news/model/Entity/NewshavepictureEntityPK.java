package com.football.news.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class NewshavepictureEntityPK implements Serializable {
    @Column(name = "news_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int newsId;
    @Column(name = "pictureroute")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        NewshavepictureEntityPK that = (NewshavepictureEntityPK) o;
        return newsId == that.newsId && Objects.equals(pictureroute, that.pictureroute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsId, pictureroute);
    }
}
