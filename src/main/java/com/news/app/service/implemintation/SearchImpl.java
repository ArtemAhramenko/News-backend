package com.news.app.service.implemintation;

import com.news.app.entity.Articles;
import com.news.app.repository.ArticlesRepository;
import com.news.app.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchImpl implements SearchService {

    private final ArticlesRepository articlesRepository;

    public SearchImpl(final ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    @Override
    public List<Articles> searchByString(String searchString) {
        List<Articles> articlesList = articlesRepository.findAll();
        List<Articles> articlesList2 = new ArrayList<>();
        for (Articles anArticlesList : articlesList) {
            if (anArticlesList.getContent().contains(searchString)
                    || anArticlesList.getDescription().contains(searchString)
                    || anArticlesList.getTitle().contains(searchString)) {
                articlesList2.add(anArticlesList);
            }
        }
        return articlesList2;
    }
}
