package com.news.app.controller;

import com.news.app.entity.Articles;
import com.news.app.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticlesService articlesService;

    @RequestMapping(path="/getarticle")
    public List<Articles> getAllArticles(){
        List<Articles> news = articlesService.getAllArticles();
        return news;
    }

    @RequestMapping(path="/addarticle")
    public void addArticles(Articles articles){
        articlesService.addArticle(articles);
    }
}
