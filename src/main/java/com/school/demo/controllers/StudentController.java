package com.school.demo.controllers;

import com.school.demo.converter.GenericConverter;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.StudentService;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.StudentView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StudentController {

    private final StudentService service;
    private final GenericConverter converter;

    //work
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentView> getStudent(@PathVariable("studentId") long id) {
        log.info("Endpoint '/{studentId}' reached.");

        log.debug("Calling service.");
        StudentView view = converter.convert(service.get(id), StudentView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PostMapping("/create")
    public ResponseEntity<StudentView> createStudent(@RequestBody CreatePersonModel model) {
        log.info("Endpoint '/create' reached.");

        log.debug("Calling service.");
        StudentView view = converter.convert(service.create(model), StudentView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PutMapping("/{studentId}/edit")
    public ResponseEntity<StudentView> editStudent(@PathVariable("studentId") long id, @RequestBody CreatePersonModel model) {
        log.info("Endpoint '/{studentId}/edit' reached.");

        log.debug("Calling service.");
        StudentView view = converter.convert(service.edit(id, model), StudentView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @DeleteMapping("/{studentId}/delete")
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") long id) {
        log.info("Endpoint '/{studentId}/delete' reached.");

        log.debug("Calling service.");
        boolean flag = service.delete(id);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/get/grades")
    public List<CourseIdAndGradesView> getGrades(@PathVariable("id") long id) {
        log.info("Endpoint '/{id}/get/grades' reached.");

        log.debug("Calling service.");
        return service.getAllGrades(id);
    }

    @GetMapping("/{id}/get/teachers")
    public List<TeacherView> getTeachers(@PathVariable("id") long id) {
        log.info("Endpoint '/{id}/get/teachers' reached.");

        log.debug("Calling service.");
        return service.getAllTeachers(id);
    }
}
