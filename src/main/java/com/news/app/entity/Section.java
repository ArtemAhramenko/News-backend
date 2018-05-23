package com.news.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String heading;
    @JsonIgnore
    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER)
    private List<Articles> news;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Articles> getNews() {
        return news;
    }

    public void setNews(List<Articles> news) {
        this.news = news;
    }

}
