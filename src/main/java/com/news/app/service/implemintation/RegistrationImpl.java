package com.news.app.service.implemintation;

import com.news.app.entity.EnumRoles;
import com.news.app.entity.User;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.entity.dto.registration.RegistrationRequestDto;
import com.news.app.exception.registration.EmailAlreadyExistException;
import com.news.app.exception.registration.UsernameAlreadyExistException;
import com.news.app.repository.RegistrationRepository;
import com.news.app.repository.UserRepository;
import com.news.app.service.RegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationImpl implements RegistrationService {

    @Autowired
    private  MailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public RegistrationRequestDto register(RegistrationRequestDto registrationRequestDto) {
        checkExisting(registrationRequestDto);
        User newUser = new User();
        newUser.setUsername(registrationRequestDto.getUsername());
        newUser.setEmail(registrationRequestDto.getEmail());
        newUser.setPassword(encodePass(registrationRequestDto.getPassword()));
        newUser.setSendConfirm(false);
        newUser.setRole(EnumRoles.USER);
        newUser.setActivationCode(UUID.randomUUID().toString());
        registrationRepository.save(newUser);
        if (!StringUtils.isEmpty(newUser.getEmail())) {
                String message = String.format(
                        "Hello, %s.\n" +
                                "Welcome to NewsSite. To confirm your registration visit next link: http://localhost:8080/activate/%s",
                        newUser.getUsername(),
                        newUser.getActivationCode()
                );
                mailSender.sendMail(newUser.getEmail(), "Confirm registration", message);
        }

        return registrationRequestDto;
    }

    private void checkExisting(RegistrationRequestDto registrationRequestDto) {
        checkUsernameExist(registrationRequestDto.getUsername());
        checkEmailExist(registrationRequestDto.getEmail());
    }

    private void checkUsernameExist(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            throw new UsernameAlreadyExistException();
        }
        RegistrationRequestDto registrationData = registrationRepository.findByUsername(username);
        if (registrationData != null) {
            throw new UsernameAlreadyExistException();
        }
    }

    private void checkEmailExist(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            throw new EmailAlreadyExistException();
        }
        RegistrationRequestDto registrationData = registrationRepository.findByEmail(email);
        if(registrationData != null) {
            throw new EmailAlreadyExistException();
        }
    }

    @Override
    public LoginRequestDto register(LoginRequestDto loginRequestDto) {
        User newUser = new User();
        newUser.setUsername(loginRequestDto.getUsername());
        newUser.setPassword(encodePass(loginRequestDto.getPassword()));
        newUser.setSendConfirm(true);
        newUser.setRole(EnumRoles.READER);
        registrationRepository.save(newUser);

        return loginRequestDto;
    }

    @Override
    public boolean activateUser(String code) {
        User user = registrationRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }
        user.setRole(EnumRoles.READER);
        user.setActivationCode(null);
        user.setSendConfirm(true);
        registrationRepository.save(user);

        return true;
    }

    private String encodePass(String pass) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(pass);
    }
}
