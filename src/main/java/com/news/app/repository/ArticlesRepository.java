package com.news.app.repository;

import com.news.app.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    Articles findById(Long id);
    List<Articles> getAllByUserId(Long id);
    List<Articles> getAllBySectionId(Long id);
}
