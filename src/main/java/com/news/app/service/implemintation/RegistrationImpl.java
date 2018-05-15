package com.news.app.service.implemintation;

import com.news.app.entity.User;
import com.news.app.entity.UserRole;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.entity.dto.RegistrationRequestDto;
import com.news.app.repository.RegistrationRepository;
import com.news.app.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class RegistrationImpl implements RegistrationService {

    @Autowired
    private  MailSender mailSender;

    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public RegistrationRequestDto register(RegistrationRequestDto registrationRequestDto) {
        User newUser = new User();
        newUser.setUsername(registrationRequestDto.getUsername());
        newUser.setEmail(registrationRequestDto.getEmail());
        newUser.setPassword(encodePass(registrationRequestDto.getPassword()));
        newUser.setSendConfirm(false);
        newUser.setRole(UserRole.READER);
        newUser.setActivationCode(UUID.randomUUID().toString());
        registrationRepository.save(newUser);
        if (!StringUtils.isEmpty(newUser.getEmail())) {
            String message = String.format(
                    "Hello, %s \n" +
                            "Welcome to NewsSite. To confirm your registration visit next link: http://localhost:8080/activate/%s",
                    newUser.getUsername(),
                    newUser.getActivationCode()
            );

            mailSender.sendMail(newUser.getEmail(), "Confirm registration", message);
        }
        return registrationRequestDto;
    }

    @Override
    public LoginRequestDto register(LoginRequestDto loginRequestDto) {
        User newUser = new User();
        newUser.setUsername(loginRequestDto.getUsername());
        newUser.setPassword(encodePass(loginRequestDto.getPassword()));
        newUser.setSendConfirm(true);
        newUser.setRole(UserRole.READER);
        registrationRepository.save(newUser);
        return loginRequestDto;
    }

    @Override
    public boolean activateUser(String code) {
        User user = registrationRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        registrationRepository.save(user);

        return true;
    }

    private String encodePass(String pass) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(pass);
    }
}
