package com.news.app.controller;

import com.news.app.entity.Articles;
import com.news.app.entity.dto.registration.RegistrationRequestDto;
import com.news.app.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path="/search/{searchString}")
    public List<Articles> search (@PathVariable String searchString){
        return searchService.searchByString(searchString);
    }

}
