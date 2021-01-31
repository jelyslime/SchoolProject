package com.school.demo.controllers;

import com.school.demo.services.ParentServiceImpl;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parents")
@AllArgsConstructor
public class ParentController {
    ParentServiceImpl service;

//    @GetMapping("parent/{id}")
//    ParentView getStudent(@PathVariable("id") long id) {
//        Parent parent = repository.findById(id).orElse(new Parent());
//        ParentDTO dto = mapper.map(parent, ParentDTO.class);
//        ParentView view = mapper.map(dto, ParentView.class);
//
//        return view;
//    }

    @GetMapping("/{id}/getGrades")
    Map<String, List<CourseIdAndGradesView>> getGrades(@PathVariable("id") long id) {
        return service.getAllGrades(id);
    }

    @GetMapping("/{id}/getTeachers")
    Map<String, List<TeacherView>> getTeachers(@PathVariable("id") long id) {
        return service.getAllTeachers(id);
    }
}
