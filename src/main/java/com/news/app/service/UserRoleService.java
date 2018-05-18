package com.news.app.service;

import com.news.app.entity.UserRole;

import java.util.List;

public interface UserRoleService {

    List<UserRole> getRoleNames(Long userId);

}
