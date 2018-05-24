package com.news.app.controller;

import com.news.app.entity.Section;
import com.news.app.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @GetMapping(path="/getallsections")
    public List<Section> getAllSections(){
        return sectionService.getAllSections();
    }

}
