package com.news.app.service;

import com.news.app.entity.Articles;

import java.util.List;
import java.util.Optional;

public interface ArticlesService {
    List<Articles> getAllArticles();
    void addArticle(Articles article);
    Articles getArticleId(Long id);
}
