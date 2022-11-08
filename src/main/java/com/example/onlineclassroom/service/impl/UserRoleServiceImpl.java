package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.UserRole;
import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;
import com.example.onlineclassroom.repository.UserRoleRepository;
import com.example.onlineclassroom.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public void initUserRoles() {
        if (userRoleRepository.count() != 0){
            return;
        }
        Arrays.stream(UserRoleEnum.values())
                .forEach(userRoleEnum -> {
                    UserRole userRole = new UserRole();
                    userRole.setRole(userRoleEnum);
                    userRoleRepository.save(userRole);
                });
    }

    @Override
    public UserRole getRole(UserRoleEnum role) {
        return userRoleRepository.findByRole(UserRoleEnum.valueOf(role.name()))
                .orElse(null);
    }
}
