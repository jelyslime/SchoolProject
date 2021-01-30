package com.school.demo.controllers;

import com.school.demo.dto.StudentDTO;
import com.school.demo.entity.Student;
import com.school.demo.repository.StudentRepository;
import com.school.demo.views.StudentView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentRepository repository;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{id}")
    StudentView getStudent(@PathVariable("id") long id) {
        Student student = repository.findById(id).orElse(new Student());
        StudentDTO dto = mapper.map(student, StudentDTO.class);
        StudentView view = mapper.map(dto, StudentView.class);

        return view;
    }
}
