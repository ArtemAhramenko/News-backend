package com.news.app.service;

import com.news.app.entity.Articles;

import java.util.List;
import java.util.Optional;

public interface ArticlesService {

    List<Articles> getAllArticles();

    Optional<Articles> getArticleId (Long id);

    void addArticle(Articles article);
}
