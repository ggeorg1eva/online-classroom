package com.example.onlineclassroom.web;

import com.example.onlineclassroom.model.binding.AssignmentCreateBindingModel;
import com.example.onlineclassroom.model.binding.GradeAddBindingModel;
import com.example.onlineclassroom.model.service.AssignmentServiceModel;
import com.example.onlineclassroom.model.service.GradeServiceModel;
import com.example.onlineclassroom.model.view.AssignmentViewTeacher;
import com.example.onlineclassroom.model.view.SchoolClassView;
import com.example.onlineclassroom.model.view.StudentView;
import com.example.onlineclassroom.service.*;
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
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AssignmentService assignmentService;
    private final SubjectService subjectService;
    private final UserService userService;
    private final GradeService gradeService;
    private final ModelMapper modelMapper;

    public TeacherController(TeacherService teacherService, StudentService studentService, AssignmentService assignmentService, SubjectService subjectService, UserService userService, GradeService gradeService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.assignmentService = assignmentService;
        this.subjectService = subjectService;
        this.userService = userService;
        this.gradeService = gradeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my-assignments")
    public String viewAllAssignmentsByTeacher(@AuthenticationPrincipal UserDetails principal, Model model) {
        String principalEgn = userService.getUserEgnByUsername(principal.getUsername());

        List<AssignmentViewTeacher> assignmentViewTeachers = assignmentService.getAllAssignmentViewsByTeacherEgn(principalEgn);

        model.addAttribute("assignments", assignmentViewTeachers);
        return "all-assignments-by-teacher";

    }

    @ModelAttribute
    public AssignmentCreateBindingModel assignmentCreateBindingModel() {
        return new AssignmentCreateBindingModel();
    }

    @GetMapping("/assignments/create")
    public String createAssignment() {
        return "create-assignment";
    }

    @PostMapping("/assignments/create")
    public String createAssignmentPost(@Valid AssignmentCreateBindingModel assignmentCreateBindingModel, BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal) {
        if (bindingResult.hasErrors()) {
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
    public String deleteAssignmentById(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        //if there are grades on this assignment, it cannot be deleted
        boolean isAssignmentGraded = gradeService.findGradesByAssignmentId(id);
        model.addAttribute("cannotDelete", false);

        if (isAssignmentGraded){
            redirectAttributes.addFlashAttribute("cannotDelete", true);
            return "redirect:/teachers/my-assignments";
        }
        assignmentService.deleteAssignmentById(id);
        return "redirect:/teachers/my-assignments";
    }

    @GetMapping("/classes")
    public String getClassesByTeacherEgn(@AuthenticationPrincipal UserDetails principal, Model model) {
        String principalEgn = userService.getUserEgnByUsername(principal.getUsername());

        List<SchoolClassView> classes = teacherService.getClassesByTeacherEgn(principalEgn);

        model.addAttribute("classes", classes);

        return "classes-by-teacher";
    }

    @GetMapping("/grades/classes/{id}")
    public String getStudentGradesByClass(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails principal) {
        List<StudentView> students = studentService.getStudentsByClassId(id);
        // need teacherEgn to find assignments and subjectId
        String teacherEgn = userService.getUserEgnByUsername(principal.getUsername());

        students.forEach(studentView ->
                {
                    studentView.setGradesBySubject(gradeService.getGradeViewsByStudentIdAndSubjectId(studentView.getId(),
                            teacherService.getTeacherSubjectViewByEgn(teacherEgn).getId()));
                    studentView.setGradesAsString(gradeService.getGradesViewListAsString(studentView.getGradesBySubject()));
                }
        );

        model.addAttribute("students", students);
        //so that a teacher can choose an assignment for which they want to add a grade to a student
        // in the addGradeToStudentWithId() method
        model.addAttribute("assignments", assignmentService.getAllAssignmentsNameAndDueDateByTeacherEgn(
                teacherEgn
        ));
        return "class-students-grades";
    }

    @ModelAttribute
    public GradeAddBindingModel gradeAddBindingModel() {
        return new GradeAddBindingModel();
    }

    @PostMapping("/grades/add/{stId}")
    public String addGradeToStudentWithId(@PathVariable Long stId, @AuthenticationPrincipal UserDetails principal,
                                          @Valid GradeAddBindingModel gradeAddBindingModel, BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {

        String principalEgn = userService.getUserEgnByUsername(principal.getUsername());
        //needed for the redirect URl
        Long studentSchoolClass = studentService.getSchoolClassIdByStudentId(stId);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("gradeAddBindingModel", gradeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gradeAddBindingModel", bindingResult);
            return "redirect:/teachers/grades/classes/" + studentSchoolClass;
        }

        gradeAddBindingModel.setStudentId(stId);
        gradeAddBindingModel.setDateOfCreation(LocalDateTime.now());
        gradeAddBindingModel.setSubjectName(teacherService.getTeacherSubjectViewByEgn(principalEgn).getName());
        GradeServiceModel serviceModel = modelMapper.map(gradeAddBindingModel, GradeServiceModel.class);
        //if this is not explicitly set to null, ModelMapper class sets it to the value found in studentId field
        serviceModel.setId(null);

        if (gradeAddBindingModel.getAssignmentNameAndDueDateString().equalsIgnoreCase("none") ||
                gradeAddBindingModel.getAssignmentNameAndDueDateString().equalsIgnoreCase("choose")) {
            serviceModel.setAssignmentNameAndDueDate(null);
        }

        boolean isGradeCreated = gradeService.createGrade(serviceModel);

        if (!isGradeCreated){
            redirectAttributes.addFlashAttribute("gradeExists", true);
            return "redirect:/teachers/grades/classes/" + studentSchoolClass;
        }

        return "redirect:/teachers/grades/classes/" + studentSchoolClass;
    }

}
