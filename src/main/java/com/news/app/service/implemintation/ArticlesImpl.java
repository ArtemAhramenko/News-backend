package com.news.app.service.implemintation;

import com.news.app.entity.Articles;
import com.news.app.repository.ArticlesRepository;
import com.news.app.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticlesImpl implements ArticlesService {

    @Autowired
    private ArticlesRepository articlesRepository;

    public List<Articles> getAllArticles(){
        List<Articles> articles = new ArrayList<>();
        articlesRepository.findAll().forEach(articles :: add);
        return articles;
    }

    public Optional<Articles> getArticleId(Long id) {
        return articlesRepository.findById(id);
    }

    public void addArticle(Articles article){
        articlesRepository.save(article);
    }

}
