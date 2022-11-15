package com.example.onlineclassroom.model.service;

import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
public class UserServiceModel {

    private String username;

    private String password;

    private String email;

    private UserRoleEnum userRole;

    private String egn;
}
