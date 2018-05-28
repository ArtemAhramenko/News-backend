package com.news.app.controller;

import com.news.app.entity.ArticleCreate;
import com.news.app.entity.Articles;
import com.news.app.service.ArticlesService;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping(path="/getarticle")
    public List<Articles> getAllArticles(){
        return articlesService.getAllArticles();
    }

    @PreAuthorize("hasAnyAuthority('WRITER', 'ADMIN')")
    @PostMapping(path="/addarticle")
    public void addArticles(@RequestBody ArticleCreate articleCreate){
        articlesService.addArticle(articleCreate);
    }

    @PreAuthorize("hasAnyAuthority('WRITER', 'ADMIN')")
    @GetMapping(path="/create")
    public String createArticlePage(){
        return JSONParser.quote("auth");
    }

    @PostMapping(path = "/getarticleid/{id}")
    public Articles getArticleId(@PathVariable Long id){
        return articlesService.getArticleById(id);
    }

    @PostMapping(path = "/getsection/{id}")
    public List<Articles> getArticleBySectionId(@PathVariable Long id){
        return articlesService.getArticleBySectionId(id);
    }

    @GetMapping(path = "/getpopulararticle")
    public List<Articles> getPopularArticle(){
        return articlesService.getPopularArticles();
    }

}