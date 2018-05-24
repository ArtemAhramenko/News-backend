package com.news.app.service.implemintation;

import com.news.app.entity.User;
import com.news.app.entity.dto.UserChangeParamsDto;
import com.news.app.repository.ArticlesRepository;
import com.news.app.repository.UserRepository;
import com.news.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    private final ArticlesRepository articlesRepository;

    @Autowired
    public UserImpl(UserRepository userRepository, ArticlesRepository articlesRepository) {
        this.userRepository = userRepository;
        this.articlesRepository = articlesRepository;
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

    @Override
    public UserChangeParamsDto getUserById(Long id) {
        User user = userRepository.findOne(id);
        UserChangeParamsDto userChangeParamsDto = new UserChangeParamsDto();
        return makeDto(user, userChangeParamsDto);
    }

    private UserChangeParamsDto makeDto(User user, UserChangeParamsDto userChangeParamsDto) {
        userChangeParamsDto.setPassword(user.getPassword());
        userChangeParamsDto.setProfileImg(user.getProfileImg());
        userChangeParamsDto.setUsername(user.getUsername());
        userChangeParamsDto.setNews(articlesRepository.getAllByUserId(user.getId()));
        userChangeParamsDto.setEmail(user.getEmail());
        userChangeParamsDto.setAlias(user.getAlias());
        return userChangeParamsDto;
    }

    @Override
    public void changeUser(User user) {
        User myUser = userRepository.findOne(user.getId());
        myUser.setAlias(user.getAlias());
        myUser.setPassword(user.getPassword());
    }
}
