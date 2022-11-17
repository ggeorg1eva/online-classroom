package com.example.onlineclassroom.web;

import com.example.onlineclassroom.model.binding.AssignmentCreateBindingModel;
import com.example.onlineclassroom.model.service.AssignmentServiceModel;
import com.example.onlineclassroom.model.view.AssignmentView;
import com.example.onlineclassroom.service.AssignmentService;
import com.example.onlineclassroom.service.TeacherService;
import com.example.onlineclassroom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final AssignmentService assignmentService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public TeacherController(TeacherService teacherService, AssignmentService assignmentService, UserService userService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.assignmentService = assignmentService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my-assignments")
    public String viewAllAssignmentsByTeacher(@AuthenticationPrincipal UserDetails principal, Model model){
        String principalEgn = userService.getUserEgnByUsername(principal.getUsername());

        List<AssignmentView> assignmentViews = assignmentService.getAllAssignmentViewsByTeacherEgn(principalEgn);

        model.addAttribute("assignments", assignmentViews);
        return "all-assignments";

    }

    @ModelAttribute
    public AssignmentCreateBindingModel assignmentCreateBindingModel(){
        return new AssignmentCreateBindingModel();
    }

    @GetMapping("/assignments/create")
    public String createAssignment(){
        return "create-assignment";
    }

    @PostMapping("/assignments/create")
    public String createAssignmentPost(@Valid AssignmentCreateBindingModel assignmentCreateBindingModel, BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("assignmentCreateBindingModel", assignmentCreateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.assignmentCreateBindingModel", bindingResult);
            return "redirect:/teachers/assignments/create";
        }

        AssignmentServiceModel serviceModel = modelMapper.map(assignmentCreateBindingModel, AssignmentServiceModel.class);
        String principalEgn = userService.getUserEgnByUsername(principal.getUsername());
        serviceModel.setTeacherEgn(principalEgn);
        serviceModel.setCreationDate(LocalDate.now());

        assignmentService.createAssignment(serviceModel);

        return "redirect:/teachers/my-assignments";
    }

    @DeleteMapping("/assignments/delete/{id}")
    public String deleteAssignmentById(@PathVariable Long id){
        assignmentService.deleteAssignmentById(id);

        return "redirect:/teachers/my-assignments";
    }
}
