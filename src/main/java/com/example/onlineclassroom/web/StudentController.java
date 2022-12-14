package com.example.onlineclassroom.web;

import com.example.onlineclassroom.model.view.AssignmentViewStudent;
import com.example.onlineclassroom.model.view.GradeView;
import com.example.onlineclassroom.model.view.SubjectView;
import com.example.onlineclassroom.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final UserService userService;
    private final SchoolClassService schoolClassService;
    private final TeacherService teacherService;
    private final AssignmentService assignmentService;
    private final GradeService gradeService;
    private final SubjectService subjectService;
    private final ModelMapper modelMapper;

    public StudentController(StudentService studentService, UserService userService, SchoolClassService schoolClassService, TeacherService teacherService, AssignmentService assignmentService, GradeService gradeService, SubjectService subjectService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.userService = userService;
        this.schoolClassService = schoolClassService;
        this.teacherService = teacherService;
        this.assignmentService = assignmentService;
        this.gradeService = gradeService;
        this.subjectService = subjectService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my-subjects")
    public String getSubjectsByStudentEgn(@AuthenticationPrincipal UserDetails principal, Model model) {
        String studentEgn = userService.getUserEgnByUsername(principal.getUsername());
        Long schoolClassId = studentService.getSchoolClassIdByStudentEgn(studentEgn);

        List<SubjectView> subjects = schoolClassService.getSubjectsBySchoolClassId(schoolClassId);

        model.addAttribute("subjects", subjects);
        return "student-subjects";
    }

    @GetMapping("/my-subjects/{id}/assignments")
    public String getAssignmentsByTeacherId(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal, Model model) {
        String studentEgn = userService.getUserEgnByUsername(principal.getUsername());
        Long schoolClassId = studentService.getSchoolClassIdByStudentEgn(studentEgn);
        Long teacherId = teacherService.getTeacherIdBySchoolClassIdAndSubjectId(schoolClassId, id);


        List<AssignmentViewStudent> assignments = assignmentService
                .getAllAssignmentViewsByTeacherId(teacherId)
                .stream()
                //studentEgn is needed so that the grade of this student for this assignment can be found if it exists
                //some grades are related to assignments but assignments are not related to the grades because one assignment is not
                //related to only one student
                .peek(view -> view.setGrade(gradeService.getGradeEnumByAssignmentIdAndStudentEgn(view.getId(), studentEgn)))
                .collect(Collectors.toList());

        model.addAttribute("subName", subjectService.getSubjectNameById(id));
        model.addAttribute("assignments", assignments);
        return "assignments-by-subject";
    }

    @GetMapping("/my-subjects/{id}/grades")
    public String getGradesByStudentIdAndSubjectId(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal, Model model) {
        String studentEgn = userService.getUserEgnByUsername(principal.getUsername());
        Long studentId = studentService.getStudentIdByEgn(studentEgn);

        List<GradeView> grades = gradeService.getGradeViewsByStudentIdAndSubjectId(studentId, id);

        model.addAttribute("grades", grades);
        model.addAttribute("subName", subjectService.getSubjectNameById(id));
        return "grades-by-subject";
    }
}
