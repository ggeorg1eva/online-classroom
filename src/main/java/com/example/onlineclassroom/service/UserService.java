package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.service.UserServiceModel;

public interface UserService {
    void initAdmin();

    String registerUser(UserServiceModel serviceModel);

    String getUserEgnByUsername(String username);
}
