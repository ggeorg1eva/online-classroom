package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserProfileView {
    private String username;

    private String email;

    private String role;

    private String firstName;

    private String lastName;

    private String egn;

    private Character gender;
}
