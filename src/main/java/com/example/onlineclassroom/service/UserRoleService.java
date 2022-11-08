package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.entity.UserRole;
import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;

public interface UserRoleService {
    void initUserRoles();
    UserRole getRole(UserRoleEnum role);
}
