package com.school.demo.controllers;

import com.school.demo.models.CreateParentModel;
import com.school.demo.services.ParentServiceImpl;
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
    private final ModelMapper mapper;

    @GetMapping("/{parentId}")
    public ParentView getDirector(@PathVariable("parentId") long id) {
        return mapper.map(service.get(id), ParentView.class);
    }

    @PostMapping("/create")
    public ParentView createDirector(@RequestBody CreateParentModel model) {
        return mapper.map(service.create(model), ParentView.class);
    }

    @PutMapping("/{parentId}/edit")
    public ParentView editDirector(@PathVariable("parentId") long id, @RequestBody CreateParentModel model) {
        return mapper.map(service.edit(id, model), ParentView.class);
    }

    @DeleteMapping("/{parentId}/delete")
    public ResponseEntity<Void> deleteDirector(@PathVariable("parentId") long id) {
        boolean flag = service.delete(id);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{parentId}/addChild/{StudentId}")
    public ResponseEntity<Void> addChild(@PathVariable("parentId") long parentId, @PathVariable("StudentId") long studentId) {

        boolean flag = service.addChild(parentId, studentId);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{parentId}/removeChild/{StudentId}")
    public ResponseEntity<Void> removeChild(@PathVariable("parentId") long parentId, @PathVariable("StudentId") long studentId) {

        boolean flag = service.removeChild(parentId, studentId);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/getGrades")
    Map<String, List<CourseIdAndGradesView>> getGrades(@PathVariable("id") long id) {
        return service.getAllGrades(id);
    }

    @GetMapping("/{id}/getTeachers")
    Map<String, List<TeacherView>> getTeachers(@PathVariable("id") long id) {
        return service.getAllTeachers(id);
    }
}
