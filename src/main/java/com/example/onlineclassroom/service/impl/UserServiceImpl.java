package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.User;
import com.example.onlineclassroom.model.entity.UserRole;
import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;
import com.example.onlineclassroom.repository.UserRepository;
import com.example.onlineclassroom.repository.UserRoleRepository;
import com.example.onlineclassroom.service.UserRoleService;
import com.example.onlineclassroom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void initAdmin() {
        if (userRepository.count() != 0){
            return;
        }
        UserRole adminRole = userRoleService.getRole(UserRoleEnum.ADMIN);

        if (adminRole != null){
            User admin = new User();
            admin.setEgn("7801254763");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setUsername("admin");
            admin.setUserRole(adminRole);

            userRepository.save(admin);
        }
    }
}
