package com.football.news.model.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "picture", schema = "football_news", catalog = "")
public class PictureEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pictureroute")
    private String pictureroute;

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
        PictureEntity that = (PictureEntity) o;
        return Objects.equals(pictureroute, that.pictureroute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pictureroute);
    }
}
