package com.school.demo.controllers;

import com.school.demo.converter.GenericConverter;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.StudentService;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.SchoolView;
import com.school.demo.views.StudentView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService service;
    private final GenericConverter converter;

    //work
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentView> getStudent(@PathVariable("studentId") long id) {
        StudentView view = converter.convert(service.get(id),StudentView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PostMapping("/create")
    public ResponseEntity<StudentView> createStudent(@RequestBody CreatePersonModel model) {
        StudentView view = converter.convert(service.create(model),StudentView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PutMapping("/{studentId}/edit")
    public ResponseEntity<StudentView> editStudent(@PathVariable("studentId") long id, @RequestBody CreatePersonModel model) {
        StudentView view = converter.convert(service.edit(id, model),StudentView.class);

        return ResponseEntity.ok().body(view);
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
