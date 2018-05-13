package com.news.app.service.implemintation;

import com.news.app.entity.User;
import com.news.app.entity.dto.RegistrationRequestDto;
import com.news.app.repository.RegistrationRepository;
import com.news.app.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public RegistrationRequestDto register(RegistrationRequestDto registrationRequestDto) {
        User newUser = new User();
        newUser.setUsername(registrationRequestDto.getUsername());
        newUser.setEmail(registrationRequestDto.getEmail());
        newUser.setPassword(registrationRequestDto.getPassword());
        registrationRepository.save(newUser);
        return registrationRequestDto;
    }
}