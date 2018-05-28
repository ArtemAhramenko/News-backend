package com.news.app.controller;

import com.news.app.entity.Rating;
import com.news.app.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RatingController{


    @Autowired
    private RatingService ratingService;

    @PostMapping(path="/addrating")
    public void addArticles(@RequestBody Rating rating){
        ratingService.addRating(rating);
    }
}
