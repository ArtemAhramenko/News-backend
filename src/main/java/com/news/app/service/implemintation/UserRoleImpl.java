package com.news.app.service.implemintation;

import com.news.app.entity.UserRole;
import com.news.app.repository.UserRoleRepository;
import com.news.app.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleImpl implements UserRoleService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> getRoleNames(Long userId) {
        return new ArrayList<>(userRoleRepository.findAll());
    }
}
