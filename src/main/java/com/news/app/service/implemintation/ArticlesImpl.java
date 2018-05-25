package com.news.app.service.implemintation;

import com.news.app.entity.Articles;
import com.news.app.entity.Section;
import com.news.app.repository.ArticlesRepository;
import com.news.app.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticlesImpl implements ArticlesService {

    private final ArticlesRepository articlesRepository;

    @Autowired
    public ArticlesImpl(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    @Override
    public  List<Articles> getAllArticles(){
        List<Articles> articles = articlesRepository.findAll();
        articles.sort(Comparator.comparing(Articles::getCreatedDate).reversed());
        return articles;
    }

    public List<Articles> getPopularArticles(){
        List<Articles> popularArticles = articlesRepository.findAll();
        popularArticles.sort(Comparator.comparing(Articles::getRating).reversed());
        popularArticles = popularArticles.subList(0,3);
        return popularArticles;
    }

    public void addArticle(Articles article){
        articlesRepository.save(article);
    }

    public Articles getArticleById(Long id) { return articlesRepository.findOne(id); }


    public List<Articles> getArticleBySectionId(Long id){
        return articlesRepository.getAllBySectionId(id);
    }

}
