package com.example.onlineclassroom.model.binding;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EditPasswordBindingModel {
    @NotBlank
    @Size(min = 4, max = 15)
    private String newPassword;

    @NotBlank
    @Size(min = 4, max = 15)
    private String confirmNewPassword;
}
