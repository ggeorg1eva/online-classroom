package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.User;
import com.example.onlineclassroom.model.entity.UserRole;
import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;
import com.example.onlineclassroom.model.service.UserServiceModel;
import com.example.onlineclassroom.repository.UserRepository;
import com.example.onlineclassroom.service.StudentService;
import com.example.onlineclassroom.service.TeacherService;
import com.example.onlineclassroom.service.UserRoleService;
import com.example.onlineclassroom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRoleService userRoleService, PasswordEncoder passwordEncoder, TeacherService teacherService, StudentService studentService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }


    @Override
    public void initAdmin() {
        if (userRepository.count() != 0) {
            return;
        }
        UserRole adminRole = userRoleService.getRole(UserRoleEnum.ADMIN);

        if (adminRole != null) {
            User admin = new User();
            admin.setEgn("7801254763");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setUsername("admin");
            admin.setUserRole(adminRole);

            userRepository.save(admin);
        }
    }

    @Override
    public String registerUser(UserServiceModel serviceModel) {
        User user = userRepository.findByUsernameOrEmailOrEgn(serviceModel.getUsername(),
                serviceModel.getEmail(), serviceModel.getEgn()).orElse(null);

        if (user != null) {
            return "exists";
        }

        if (serviceModel.getUserRole().equals(UserRoleEnum.TEACHER)) {
            boolean isTeacherRegistered = teacherService.registerTeacher(serviceModel.getEgn());
            if (!isTeacherRegistered){
                return "teacher-not-found";
            }
        }

        if (serviceModel.getUserRole().equals(UserRoleEnum.STUDENT)) {
            boolean isTeacherRegistered = studentService.registerStudent(serviceModel.getEgn());
            if (!isTeacherRegistered){
                return "student-not-found";
            }
        }

        User userToRegister = modelMapper.map(serviceModel, User.class);
        userToRegister.setUserRole(userRoleService.getRole(serviceModel.getUserRole()));
        userRepository.save(userToRegister);

        return "successful";
    }

    @Override
    public String getUserEgnByUsername(String username) {
       return Objects.requireNonNull(userRepository.findByUsername(username).orElse(null))
                .getEgn();
    }
}
