package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.binding.EditEmailBindingModel;
import com.example.onlineclassroom.model.binding.EditPasswordBindingModel;
import com.example.onlineclassroom.model.binding.EditUsernameBindingModel;
import com.example.onlineclassroom.model.service.UserServiceModel;
import com.example.onlineclassroom.model.view.UserProfileView;

public interface UserService {

    String registerUser(UserServiceModel serviceModel);

    String getUserEgnByUsername(String username);

    UserProfileView getUserViewFromUsername(String principalUsername);

    void editUsername(String principalEgn, EditUsernameBindingModel editUsernameBindingModel);

    void editEmail(String principalEgn, EditEmailBindingModel editEmailBindingModel);

    void editPassword(String principalEgn, EditPasswordBindingModel editPasswordBindingModel);
}
