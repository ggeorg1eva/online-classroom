package com.example.onlineclassroom.model.binding;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EditUsernameBindingModel {

    @Size(min = 3, max = 20)
    @NotBlank
    private String newUsername;
}
