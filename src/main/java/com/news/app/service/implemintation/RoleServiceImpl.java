package com.news.app.service.implemintation;

import com.news.app.entity.Role;
import com.news.app.repository.RoleRepository;
import com.news.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String user) {
        return roleRepository.findByName(user);
    }
}
