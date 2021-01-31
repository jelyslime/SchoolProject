package com.school.demo.controllers;

import com.school.demo.services.TeacherServiceImpl;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
public class TeacherController {

    TeacherServiceImpl service;

//    @GetMapping("/{id}")
//    public TeacherView getTeacher(@PathVariable("id") long id) {
//        return mapper.map(repository.findById(id).orElse(new Teacher()), TeacherView.class);
//    }

    @GetMapping("/{id}/getAllStudentGrades")
    Map<Long, Map<PersonNamesView, List<GradeAsValueView>>>getAllStudentGradesByTeacherId(@PathVariable("id") long id){
        return service.getAllStudentGrades(id);
    }

    @PostMapping("/{id}/course/{course_id}/addGrade/{grade}/toStudent/{student_id}")
    ResponseEntity<Void> addGrade(@PathVariable("id") long id,@PathVariable("course_id") long course_id,
                              @PathVariable("grade") double grade,@PathVariable("student_id") long student_id){

        service.addGrade(id,course_id,grade,student_id);
        return ResponseEntity.ok().build();
    }
}
