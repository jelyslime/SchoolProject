package com.school.demo.controllers;

import com.school.demo.models.CreateCourseModel;
import com.school.demo.services.CourseServiceImpl;
import com.school.demo.views.CourseView;
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

    @GetMapping("/{courseId}")
    public CourseView getCourse(@PathVariable("courseId") long id) {
        return mapper.map(service.get(id), CourseView.class);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCourse(@RequestBody CreateCourseModel model) {
        service.create(model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}/delete")
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") long id) {
        boolean flag = service.delete(id);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
