package com.school.demo.controllers;

import com.school.demo.models.CreateDirectorModel;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.StudentService;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.DirectorView;
import com.school.demo.views.StudentView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService service;
    private final ModelMapper mapper;

    //work
    @GetMapping("/{studentId}")
    public StudentView getStudent(@PathVariable("studentId") long id) {
        return mapper.map(service.get(id), StudentView.class);
    }

    //work
    @PostMapping("/create")
    public ResponseEntity<Void> createStudent(@RequestBody CreatePersonModel model) {
        service.create(model);
        return ResponseEntity.ok().build();
    }

    //work
    @PutMapping("/{studentId}/edit")
    public ResponseEntity<Void> editStudent(@PathVariable("studentId") long id, @RequestBody CreatePersonModel model) {
        service.edit(id, model);
        return ResponseEntity.ok().build();
    }

    //work
    @DeleteMapping("/{studentId}/delete")
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") long id) {
        boolean flag = service.delete(id);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/get/grades")
    public List<CourseIdAndGradesView> getGrades(@PathVariable("id") long id) {
        return service.getAllGrades(id);
    }

    @GetMapping("/{id}/get/teachers")
    public List<TeacherView> getTeachers(@PathVariable("id") long id) {
        return service.getAllTeachers(id);
    }
}
