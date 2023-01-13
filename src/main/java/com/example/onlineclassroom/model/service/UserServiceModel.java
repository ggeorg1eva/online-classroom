package com.example.onlineclassroom.model.service;

import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserServiceModel {

    private String username;

    private String password;

    private String email;

    private UserRoleEnum userRole;

    private String egn;
}
