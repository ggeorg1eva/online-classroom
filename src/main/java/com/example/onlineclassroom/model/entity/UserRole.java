package com.example.onlineclassroom.model.entity;

import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
public class UserRole extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
}
