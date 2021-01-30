package com.school.demo.controllers;

import com.school.demo.services.DirectorServiceImpl;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.ParentDirectorView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
@AllArgsConstructor
public class DirectorController {

    DirectorServiceImpl service;

    @GetMapping("/{directorId}/getAllGradesOnSubjects")
    public List<CourseIdAndGradesView> getAllGradesOnSubjects(@PathVariable("directorId") long id) {
        return service.getAllCoursesAndAllGrades(id);
    }

    @GetMapping("/{directorId}/getAllTeachers")
    public List<TeacherView> getAllTeachers(@PathVariable("directorId") long id) {
        return service.getAllTeachers(id);
    }

    @GetMapping("/{directorId}/getAllParents")
    public List<ParentDirectorView> getAllParents(@PathVariable("directorId") long id) {
        return service.getAllParents(id);
    }

}
