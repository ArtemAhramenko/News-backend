package com.news.app.controller;

import com.news.app.entity.dto.RegistrationRequestDto;
import com.news.app.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @CrossOrigin
    @PostMapping("/registration")
    public RegistrationRequestDto addUser (@RequestBody RegistrationRequestDto registrationRequestDto){
        return registrationService.register(registrationRequestDto);
    }

    @CrossOrigin
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = registrationService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found");
        }

        return "login";
    }


}
