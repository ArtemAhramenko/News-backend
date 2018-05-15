package com.news.app.repository;

import com.news.app.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationRepository extends CrudRepository<User, Long> {

    User getByUsername(String username);

}
