package com.news.app.service;

import com.news.app.entity.Articles;

import java.util.List;

public interface SearchService {

    List<Articles> searchByString(String searchString);
}
