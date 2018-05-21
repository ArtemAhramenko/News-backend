package com.news.app.service.implemintation;

import com.news.app.entity.User;
import com.news.app.repository.UserRepository;
import com.news.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users :: add);
        return users;
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }


}
