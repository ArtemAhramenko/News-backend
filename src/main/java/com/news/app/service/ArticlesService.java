package com.news.app.service;

import com.news.app.entity.ArticleCreate;
import com.news.app.entity.Articles;

import java.util.List;
import java.util.Optional;

public interface ArticlesService {
    List<Articles> getAllArticles();
    void addArticle(Articles article);
    Articles getArticleById(Long id);
    List<Articles> getArticleBySectionId(Long id);
    List<Articles> getPopularArticles();
}
