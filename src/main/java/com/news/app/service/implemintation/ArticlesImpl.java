package com.news.app.service.implemintation;

import com.news.app.entity.ArticleCreate;
import com.news.app.entity.Articles;
import com.news.app.repository.ArticlesRepository;
import com.news.app.repository.SectionRepository;
import com.news.app.repository.UserRepository;
import com.news.app.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticlesImpl implements ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArticlesImpl(ArticlesRepository articlesRepository, SectionRepository sectionRepository, UserRepository userRepository) {
        this.articlesRepository = articlesRepository;
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public  List<Articles> getAllArticles(){
        List<Articles> articles = articlesRepository.findAll();
        articles.sort(Comparator.comparing(Articles::getCreatedDate).reversed());
        return articles;
    }

    public List<Articles> getPopularArticles(){
        List<Articles> popularArticles = articlesRepository.findAll();
        popularArticles.sort(Comparator.comparing(Articles::getAverageRating).reversed());
        popularArticles = popularArticles.subList(0,10);
        return popularArticles;
    }

    public void addArticle(ArticleCreate articleCreate){
        Articles newArticle = new Articles();
        newArticle.setContent(articleCreate.getContent());
        newArticle.setCreatedDate(new Date());
        newArticle.setDescription(articleCreate.getDescription());
        newArticle.setTitle(articleCreate.getTitle());
        newArticle.setAverageRating(0.0);
        newArticle.setUser(userRepository.findOne(articleCreate.getUserId()));
        newArticle.setSection(sectionRepository.getOne(articleCreate.getSectionId()));
        articlesRepository.save(newArticle);
    }

    public Articles getArticleById(Long id) { return articlesRepository.findOne(id); }


    public List<Articles> getArticleBySectionId(Long id){
        return articlesRepository.getAllBySectionId(id);
    }

    public double getArticleRating(Long articleId){
        return   articlesRepository.findOne(articleId).getAverageRating();
    }
    public void calculateAverageRating(Long articleId, int usersCount, double userRating){
        Articles articles = articlesRepository.findOne(articleId);
        double averageRating = articles.getAverageRating();
        double newAverageRating = (((averageRating * usersCount) + userRating) / (usersCount + 1));
        articles.setAverageRating(newAverageRating);
        articlesRepository.save(articles);
    }
}
