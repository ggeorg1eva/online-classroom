package com.example.onlineclassroom.web;

import com.example.onlineclassroom.model.binding.EditEmailBindingModel;
import com.example.onlineclassroom.model.binding.EditPasswordBindingModel;
import com.example.onlineclassroom.model.binding.EditUsernameBindingModel;
import com.example.onlineclassroom.model.binding.UserRegisterBindingModel;
import com.example.onlineclassroom.model.service.UserServiceModel;
import com.example.onlineclassroom.model.view.UserProfileView;
import com.example.onlineclassroom.service.StudentService;
import com.example.onlineclassroom.service.TeacherService;
import com.example.onlineclassroom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
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
        String authority = principal.getAuthorities()
                .stream()
                .findFirst()
                .orElse(null)
                .getAuthority()
                .replace("ROLE_", "");


        String principalUsername = principal.getUsername();
        UserProfileView userView = userService.getUserViewFromUsername(principalUsername);

        if (authority.equalsIgnoreCase("teacher")){
            //explicit casting so that the fields of the child classes are available
            userView = teacherService.getTeacherProfileInfoFromUserView(userView);
        } else {
            userView = studentService.getStudentProfileInfoFromUserView(userView);
        }

        model.addAttribute("userView", userView);
        return "my-profile";
    }

    @GetMapping("/edit-profile")
    public String editProfile(){
        return "edit-profile";
    }


    @GetMapping("/edit-profile/username")
    public String editUsername(){
        return "edit-username";
    }

    @ModelAttribute
    public EditUsernameBindingModel editUsernameBindingModel(){
        return new EditUsernameBindingModel();
    }

    @PutMapping("/edit-profile/username")
    public String editUsernamePut(@Valid EditUsernameBindingModel editUsernameBindingModel,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal) throws ServletException {


        String principalEgn = userService.getUserEgnByUsername(principal.getUsername());
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("editUsernameBindingModel", editUsernameBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editUsernameBindingModel",
                    bindingResult);
            return "redirect:/users/edit-profile/username";
        }

        userService.editUsername(principalEgn, editUsernameBindingModel);
        logoutAfterProfileEdit();

        return "redirect:/users/logout";
    }

    @GetMapping("/edit-profile/email")
    public String editEmail(){
        return "edit-email";
    }

    @ModelAttribute
    public EditEmailBindingModel editEmailBindingModel(){
        return new EditEmailBindingModel();
    }

    @PutMapping("/edit-profile/email")
    public String editEmailPut(@Valid EditEmailBindingModel editEmailBindingModel,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal) throws ServletException {


        String principalEgn = userService.getUserEgnByUsername(principal.getUsername());
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("editEmailBindingModel", editEmailBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editEmailBindingModel",
                    bindingResult);
            return "redirect:/users/edit-profile/email";
        }

        userService.editEmail(principalEgn, editEmailBindingModel);
        logoutAfterProfileEdit();

        return "redirect:/users/logout";
    }

    @GetMapping("/edit-profile/password")
    public String editPassword(){
        return "edit-password";
    }

    @ModelAttribute
    public EditPasswordBindingModel editPasswordBindingModel(){
        return new EditPasswordBindingModel();
    }

    @PutMapping("/edit-profile/password")
    public String editPasswordPut(@Valid EditPasswordBindingModel editPasswordBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal) throws ServletException {


        String principalEgn = userService.getUserEgnByUsername(principal.getUsername());
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("editPasswordBindingModel", editPasswordBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editPasswordBindingModel",
                    bindingResult);
            return "redirect:/users/edit-profile/password";
        }

        if (!editPasswordBindingModel.getNewPassword().equals(editPasswordBindingModel.getConfirmNewPassword())){
            redirectAttributes.addFlashAttribute("passNotMatch", true);
            return "redirect:/users/edit-profile/password";
        }

        String newPassword = editPasswordBindingModel.getNewPassword();
        editPasswordBindingModel.setNewPassword(passwordEncoder.encode(newPassword));
        userService.editPassword(principalEgn, editPasswordBindingModel);
        logoutAfterProfileEdit();

        return "redirect:/users/logout";
    }


    private void logoutAfterProfileEdit() throws ServletException {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        attr.getRequest().logout();
    }
}
