package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.service.UserServiceModel;
import com.example.onlineclassroom.model.view.UserProfileView;

public interface UserService {
    void initAdmin();

    String registerUser(UserServiceModel serviceModel);

    String getUserEgnByUsername(String username);

    UserProfileView getUserViewFromUsername(String principalUsername);
}
