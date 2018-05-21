package com.news.app.controller;

import com.news.app.entity.Articles;
import com.news.app.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {
    private final ArticlesService articlesService;

    @Autowired
    public ArticleController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @RequestMapping(path="/getarticle")
    public List<Articles> getAllArticles(){
        return articlesService.getAllArticles();
    }

    @RequestMapping(path="/addarticle")
    public void addArticles(Articles articles){
        articlesService.addArticle(articles);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getarticleid/{id}")
    public Articles getArticleId(@PathVariable Long id){
        Articles article = articlesService.getArticleId(id);
        return article;
    }
}