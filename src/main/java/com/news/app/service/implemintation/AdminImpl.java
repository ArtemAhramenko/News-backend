package com.news.app.service.implemintation;

import com.news.app.entity.User;
import com.news.app.repository.UserRepository;
import com.news.app.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminImpl implements AdminService {

    private final UserRepository userRepository;

    public AdminImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void disableUser(Long id) {
        User user = userRepository.findOne(id);
        user.setBanned(true);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findOne(id);
        userRepository.delete(user);
    }

}
