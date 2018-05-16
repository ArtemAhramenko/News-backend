package com.news.app.service.implemintation;

import com.news.app.entity.Articles;
import com.news.app.repository.ArticlesRepository;
import com.news.app.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticlesImpl implements ArticlesService {

    private final ArticlesRepository articlesRepository;

    @Autowired
    public ArticlesImpl(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public List<Articles> getAllArticles(){
        List<Articles> articles = new ArrayList<>();
        articles.addAll(articlesRepository.findAll());
        return articles;
    }

    public void addArticle(Articles article){
        articlesRepository.save(article);
    }
}
