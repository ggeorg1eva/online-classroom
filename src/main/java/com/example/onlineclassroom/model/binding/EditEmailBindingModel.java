package com.example.onlineclassroom.model.binding;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EditEmailBindingModel {
    @NotBlank
    @Email
    private String newEmail;
}
