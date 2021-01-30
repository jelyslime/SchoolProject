package com.school.demo.controllers;

import com.school.demo.entity.Teacher;
import com.school.demo.repository.TeacherRepository;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
public class TeacherControler {
    TeacherRepository repository;
    ModelMapper mapper;

    @GetMapping("/{id}")
    public TeacherView getTeacher(@PathVariable("id") long id) {
        return mapper.map(repository.findById(id).orElse(new Teacher()), TeacherView.class);
    }
}
