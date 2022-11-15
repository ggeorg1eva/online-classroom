package com.example.onlineclassroom.model.binding;

import com.example.onlineclassroom.model.entity.UserRole;
import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRegisterBindingModel {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 4, max = 15)
    private String password;

    @NotBlank
    @Size(min = 4, max = 15)
    private String confirmPassword;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private UserRoleEnum userRole;

    @NotBlank
    @Size(min = 10, max = 10)
    private String egn;
}
