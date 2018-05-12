package com.news.app.service;

import com.news.app.entity.Articles;

import java.util.List;

public interface ArticlesService {
    List<Articles> getAllArticles();
    void addArticle(Articles article);
}
