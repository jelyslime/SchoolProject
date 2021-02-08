package com.school.demo.controllers;

import com.school.demo.converter.GenericConverter;
import com.school.demo.models.CreateCourseModel;
import com.school.demo.services.implementations.CourseServiceImpl;
import com.school.demo.views.CourseView;
import com.school.demo.views.DirectorView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

    private final ModelMapper mapper;
    CourseServiceImpl service;

    private final GenericConverter converter;

    //works
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseView> getCourse(@PathVariable("courseId") long id) {
        CourseView view = converter.convert(service.get(id),CourseView.class);

        return ResponseEntity.ok().body(view);
    }

    //works
    @PostMapping("/create")
    public ResponseEntity<CourseView> createCourse(@RequestBody CreateCourseModel model) {
        CourseView view = converter.convert(service.create(model),CourseView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @DeleteMapping("/{courseId}/delete")
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") long id) {
        boolean flag = service.delete(id);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{courseId}/assign/teacher/{teacherId}")
    public ResponseEntity<Void> addTeacherToCourse(@PathVariable long courseId, @PathVariable long teacherId) {
        service.assignTeacher(courseId, teacherId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{courseId}/assign/student/{studentID}")
    public ResponseEntity<Void> addStudentToCourse(@PathVariable long courseId, @PathVariable long studentID) {
        service.addStudent(courseId, studentID);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{courseId}/remove/student/{studentID}")
    public ResponseEntity<Void> removeStudentToCourse(@PathVariable long courseId, @PathVariable long studentID) {
        service.removeStudent(courseId, studentID);
        return ResponseEntity.ok().build();
    }

}
