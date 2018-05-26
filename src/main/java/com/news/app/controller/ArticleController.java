package com.news.app.controller;

import com.news.app.entity.ArticleCreate;
import com.news.app.entity.Articles;
import com.news.app.service.ArticlesService;
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

    @RequestMapping(path="/getarticle")
    public List<Articles> getAllArticles(){
        return articlesService.getAllArticles();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(path="/addarticle")
    public void addArticles(@RequestBody ArticleCreate articleCreate){
        System.out.println(articleCreate.getContent());
        System.out.println(articleCreate.getDescription());
        System.out.println(articleCreate.getUserId());
        System.out.println(articleCreate.getTitle());
        System.out.println(articleCreate.getCreateDate());
        System.out.println(articleCreate.getSectionId());
        articlesService.addArticle(articleCreate);
    }

    @PreAuthorize("hasAuthority('WRITER')")
    @GetMapping(path="/create")
    public String createArticlePage(){
        return "/create";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getarticleid/{id}")
    public Articles getArticleId(@PathVariable Long id){
        return articlesService.getArticleById(id);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getsection/{id}")
    public List<Articles> getArticleBySectionId(@PathVariable Long id){
        return articlesService.getArticleBySectionId(id);
    }

    @RequestMapping(path = "/getpopulararticle")
    public List<Articles> getPopularArticle(){
        return articlesService.getPopularArticles();
    }

}