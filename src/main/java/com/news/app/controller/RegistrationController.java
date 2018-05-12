package com.news.app.controller;

import com.news.app.entity.dto.RegistrationRequestDto;
import com.news.app.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public RegistrationRequestDto addUser (@RequestBody RegistrationRequestDto registrationRequestDto){
        return registrationService.register(registrationRequestDto);
    }


}
