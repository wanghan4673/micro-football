package com.football.news.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "video", schema = "football_news", catalog = "")
public class VideoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "video_id")
    private int videoId;
    @Basic
    @Column(name = "matchtag")
    private String matchtag;
    @Basic
    @Column(name = "propertag")
    private String propertag;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "cover")
    private String cover;
    @Basic
    @Column(name = "urllink")
    private String urllink;

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrllink() {
        return urllink;
    }

    public void setUrllink(String urllink) {
        this.urllink = urllink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoEntity that = (VideoEntity) o;
        return videoId == that.videoId && Objects.equals(matchtag, that.matchtag) && Objects.equals(propertag, that.propertag) && Objects.equals(title, that.title) && Objects.equals(cover, that.cover) && Objects.equals(urllink, that.urllink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, matchtag, propertag, title, cover, urllink);
    }
}
