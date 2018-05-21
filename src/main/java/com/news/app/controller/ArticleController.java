package com.news.app.controller;

import com.news.app.entity.Articles;
import com.news.app.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {
    @Autowired
    private ArticlesService articlesService;

    @RequestMapping(path="/getarticle")
    public List<Articles> getAllArticles(){
        return articlesService.getAllArticles();
    }

    @RequestMapping(path="/addarticle")
    public void addArticles(Articles articles){
        articlesService.addArticle(articles);
    }

    @RequestMapping(path = "/getarticleid/{id}")
    public Articles getArticleById(@PathVariable Long id){
        return articlesService.getArticleById(id);
    }
}