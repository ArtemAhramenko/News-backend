package com.news.app.service;

import com.news.app.entity.Articles;
import com.news.app.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticlesService {

    @Autowired
    private ArticlesRepository articlesRepository;

    private List<Articles> articles = new ArrayList<>(Arrays.asList(new Articles("title", "description", "content" )));

    public List<Articles> getAllArticles(){
        List<Articles> articles = new ArrayList<>();
        articlesRepository.findAll().forEach(articles :: add);
        return articles;
    }

//    public Articles getArticle (Long id){
//        return articlesRepository.findOne(id);
//    }

    public void addArticle(Articles article){
        articlesRepository.save(article);
    }
}
