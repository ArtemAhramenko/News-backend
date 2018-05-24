package com.news.app.service.implemintation;

import com.news.app.entity.Section;
import com.news.app.repository.SectionRepository;
import com.news.app.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionImpl implements SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<Section> getAllSections(){

        return sectionRepository.findAll();
    }
}
