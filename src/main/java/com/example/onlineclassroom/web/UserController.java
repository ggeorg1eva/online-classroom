package com.example.onlineclassroom.web;

import com.example.onlineclassroom.model.binding.UserRegisterBindingModel;
import com.example.onlineclassroom.model.service.UserServiceModel;
import com.example.onlineclassroom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
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
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
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

    @PostMapping("/login-error")
    public String loginFailed(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, userName);
        //todo fix or delete this
        // redirectAttributes.addFlashAttribute("incorrectInput",true);

        return "redirect:/login";
    }
}
