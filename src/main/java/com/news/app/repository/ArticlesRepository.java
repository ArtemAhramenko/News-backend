package com.news.app.repository;

import com.news.app.entity.Articles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticlesRepository extends CrudRepository<Articles, Long> {

}
