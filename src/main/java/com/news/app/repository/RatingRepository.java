package com.news.app.repository;

import com.news.app.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    int countRatingsByArticleId(Long articleId);
    int countRatingsByArticleIdAndUserId(Long articleId, Long UserId);
}
