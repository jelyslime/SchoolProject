package com.school.demo.controllers;

import com.school.demo.converter.GenericConverter;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.implementations.ParentServiceImpl;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.ParentView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parents")
@AllArgsConstructor
public class ParentController {
    private final ParentServiceImpl service;
    private final GenericConverter converter;

    @GetMapping("/{parentId}")
    public ResponseEntity<ParentView> getParent(@PathVariable("parentId") long id) {
        ParentView view = converter.convert(service.get(id),ParentView.class);

        return ResponseEntity.ok().body(view);
    }

    @PostMapping("/create")
    public ResponseEntity<ParentView> createParent(@RequestBody CreatePersonModel model) {
        ParentView view = converter.convert(service.create(model),ParentView.class);

        return ResponseEntity.ok().body(view);
    }

    @PutMapping("/{parentId}/edit")
    public ResponseEntity<ParentView> editParent(@PathVariable("parentId") long id, @RequestBody CreatePersonModel model) {
        ParentView view = converter.convert(service.edit(id, model),ParentView.class);

        return ResponseEntity.ok().body(view);
    }

    @DeleteMapping("/{parentId}/delete")
    public ResponseEntity<Void> deleteParent(@PathVariable("parentId") long id) {
        boolean flag = service.delete(id);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //work
    @PutMapping("/{parentId}/add/kid/{StudentId}")
    public ResponseEntity<Void> addChild(@PathVariable("parentId") long parentId, @PathVariable("StudentId") long studentId) {

        boolean flag = service.addChild(parentId, studentId);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //work
    @PutMapping("/{parentId}/remove/kid/{StudentId}")
    public ResponseEntity<Void> removeChild(@PathVariable("parentId") long parentId, @PathVariable("StudentId") long studentId) {

        boolean flag = service.removeChild(parentId, studentId);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //work
    @GetMapping("/{id}/get/kids/grades")
    Map<String, List<CourseIdAndGradesView>> getGrades(@PathVariable("id") long id) {
        return service.getAllGrades(id);
    }

    //work
    @GetMapping("/{id}/get/kids/teachers")
    Map<String, List<TeacherView>> getTeachers(@PathVariable("id") long id) {
        return service.getAllTeachers(id);
    }
}
