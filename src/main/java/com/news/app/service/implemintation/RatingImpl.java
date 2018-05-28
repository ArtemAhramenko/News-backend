package com.news.app.service.implemintation;

import com.news.app.entity.Rating;
import com.news.app.repository.ArticlesRepository;
import com.news.app.repository.RatingRepository;
import com.news.app.service.ArticlesService;
import com.news.app.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ArticlesService articlesService;

    @Autowired
    public RatingImpl(RatingRepository ratingRepository, ArticlesService articlesService) {
        this.ratingRepository = ratingRepository;
        this.articlesService = articlesService;
    }

    public void addRating(Rating rating){
        Rating newRating = new Rating();
        newRating.setArticleId(rating.getArticleId());
        newRating.setUserId(rating.getUserId());
        newRating.setUserRating(rating.getUserRating());
        int check = checkVote(newRating.getArticleId(), newRating.getUserId());
        if(check == 0) {
            int usersCount = getCount(newRating.getArticleId());
            double userRating = newRating.getUserRating();
            articlesService.calculateAverageRating(newRating.getArticleId(), usersCount, userRating);
            ratingRepository.save(newRating);
            return ;
        }
    }

    public int checkVote(Long articleId, Long userId){
         return ratingRepository.countRatingsByArticleIdAndUserId(articleId, userId);
    }

    public int getCount(Long articleId){
        return ratingRepository.countRatingsByArticleId(articleId);
    }
}
