package com.news.app.repository;

import com.news.app.entity.User;
import com.news.app.entity.dto.registration.RegistrationRequestDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends CrudRepository<User, Long> {

    RegistrationRequestDto findByUsername(String username);

    RegistrationRequestDto findByEmail(String email);

    User findByConfirmationToken(String code);
}
