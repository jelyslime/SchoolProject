package com.school.demo.controllers;

import com.school.demo.services.StudentService;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {


    private final StudentService service;


//    @GetMapping("/{id}")
//    StudentView getStudent(@PathVariable("id") long id) {
//        Student student = repository.findById(id).orElse(new Student());
//        StudentDTO dto = mapper.map(student, StudentDTO.class);
//        StudentView view = mapper.map(dto, StudentView.class);
//
//        return view;
//    }

    @GetMapping("/{id}/getGrades")
    public List<CourseIdAndGradesView> getGrades(@PathVariable("id") long id) {
        return service.getAllGrades(id);
    }

    @GetMapping("/{id}/getTeachers")
    public List<TeacherView> getTeachers(@PathVariable("id") long id) {
        return service.getAllTeachers(id);
    }
}
