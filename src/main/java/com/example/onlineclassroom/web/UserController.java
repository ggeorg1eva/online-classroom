package com.example.onlineclassroom.web;

import com.example.onlineclassroom.model.binding.UserRegisterBindingModel;
import com.example.onlineclassroom.model.service.UserServiceModel;
import com.example.onlineclassroom.model.view.TeacherProfileView;
import com.example.onlineclassroom.model.view.UserProfileView;
import com.example.onlineclassroom.service.StudentService;
import com.example.onlineclassroom.service.TeacherService;
import com.example.onlineclassroom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, TeacherService teacherService, StudentService studentService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                    bindingResult);
            return "redirect:register";
        }
        UserServiceModel serviceModel = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        serviceModel.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        String result = userService.registerUser(serviceModel);

        switch (result) {
            case "exists" -> {
                redirectAttributes.addFlashAttribute("userExists", true);
                return "redirect:register";
            }
            case "teacher-not-found" -> {
                redirectAttributes.addFlashAttribute("teacherNotFound", true);
                return "redirect:register";
            }
            case "student-not-found" -> {
                redirectAttributes.addFlashAttribute("studentNotFound", true);
                return "redirect:register";
            }
        }

        return "redirect:login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/my-profile")
    public String getMyProfileInfo(@AuthenticationPrincipal UserDetails principal, Model model) {
        String principalUsername = principal.getUsername();

        String principalEgn = userService.getUserEgnByUsername(principalUsername);

        UserProfileView userView = userService.getUserViewFromUsername(principalUsername);

        int yearOfBirth = Integer.parseInt(principalEgn.substring(0, 2));
        //if year is > 9 than the person was born before 2000, and we know for sure that they cant be a student
        if (yearOfBirth > 9) {
            TeacherProfileView teacherView =  teacherService.getTeacherProfileInfoFromUserView(userView);
            //todo finish logic for profiles, see if you need two separate methods in controller or not
        }


        return "my-profile";
    }
}
