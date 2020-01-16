package com.news.app.repository;

import com.news.app.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User getByUsername(String username);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    User findByAlias(String alias);
    User findById(Long id);
}
