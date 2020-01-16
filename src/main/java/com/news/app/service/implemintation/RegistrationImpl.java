package com.news.app.service.implemintation;

import com.news.app.entity.Role;
import com.news.app.entity.User;
import com.news.app.entity.dto.LoginRequestDto;
import com.news.app.entity.dto.registration.RegistrationRequestDto;
import com.news.app.exception.registration.EmailAlreadyExistException;
import com.news.app.exception.registration.UsernameAlreadyExistException;
import com.news.app.repository.RegistrationRepository;
import com.news.app.repository.RoleRepository;
import com.news.app.repository.UserRepository;
import com.news.app.service.RegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class RegistrationImpl implements RegistrationService {

    private final MailSender mailSender;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationImpl(final RegistrationRepository registrationRepository,
                            final MailSender mailSender,
                            final UserRepository userRepository,
                            final RoleRepository roleRepository) {
        this.registrationRepository = registrationRepository;
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public RegistrationRequestDto register(RegistrationRequestDto registrationRequestDto) {
        checkExisting(registrationRequestDto);
        User newUser = new User();
        setUserData(newUser, registrationRequestDto);
        registrationRepository.save(newUser);
        sendMessage(newUser);
        return registrationRequestDto;
    }

    /**
     * Set new user data.
     * @param user user
     * @param registrationRequestDto dto
     */
    private void setUserData(User user, RegistrationRequestDto registrationRequestDto) {
        user.setUsername(registrationRequestDto.getUsername());
        user.setEmail(registrationRequestDto.getEmail());
        user.setPassword(encodePass(registrationRequestDto.getPassword()));
        user.setAlias(registrationRequestDto.getAlias());
        user.setEnabled(false);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("READER"));
        user.setRoles(roles);
        user.setConfirmationToken(UUID.randomUUID().toString());
    }

    /**
     * For sending message on email.
     *
     * @param user new user
     */
    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s.\n" +
                            "Welcome to NewsSite. To confirm your registration visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getConfirmationToken()
            );
            mailSender.sendMail(user.getEmail(), "Confirm registration", message);
        }
    }

    /**
     * Check existing user.
     *
     * @param registrationRequestDto dto
     */
    private void checkExisting(RegistrationRequestDto registrationRequestDto) {
        checkUsernameExist(registrationRequestDto.getUsername());
        checkEmailExist(registrationRequestDto.getEmail());
    }

    private void checkUsernameExist(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        user.ifPresent(exception -> {
            throw new UsernameAlreadyExistException();
        });
        Optional<RegistrationRequestDto> requestDto = registrationRepository.findByUsername(username);
        requestDto.ifPresent(exception -> {
            throw new UsernameAlreadyExistException();
        });
    }

    private void checkEmailExist(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        user.ifPresent(exception -> {
            throw new EmailAlreadyExistException();
        });
        Optional<RegistrationRequestDto> requestDto = registrationRepository.findByEmail(email);
        requestDto.ifPresent(exception -> {
            throw new EmailAlreadyExistException();
        });
    }

    @Override
    public LoginRequestDto register(LoginRequestDto loginRequestDto) {
        User newUser = new User();
        newUser.setUsername(loginRequestDto.getUsername());
        newUser.setPassword(encodePass(loginRequestDto.getPassword()));
        newUser.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("READER"));
        newUser.setRoles(roles);
        newUser.setConfirmationToken(UUID.randomUUID().toString());
        registrationRepository.save(newUser);
        return loginRequestDto;
    }

    @Override
    public boolean activateUser(String code) {
        User user = registrationRepository.findByConfirmationToken(code);

        if (user == null) {
            return false;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("READER"));
        user.setRoles(roles);
        user.setConfirmationToken(null);
        user.setEnabled(true);
        registrationRepository.save(user);

        return true;
    }

    private String encodePass(String pass) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(pass);
    }
}
