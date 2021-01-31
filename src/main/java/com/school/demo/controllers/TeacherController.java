package com.school.demo.controllers;

import com.school.demo.services.TeacherServiceImpl;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;
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
    Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getAllStudentGradesByTeacherId(@PathVariable("id") long id) {
        return service.getAllStudentGrades(id);
    }

    @PostMapping("/{id}/course/{course_id}/addGrade/{grade}/toStudent/{student_id}")
    ResponseEntity<Void> addGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                  @PathVariable("grade") double grade, @PathVariable("student_id") long student_id) {


        try {
            service.addGrade(id, course_id, grade, student_id);
        } catch (InvalidObjectException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/course/{course_id}/gradeId/{grade_id}/updateGrade/{grade}")
    ResponseEntity<Void> updateGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                     @PathVariable("grade_id") long grade_id, @PathVariable("grade") double grade) {


        try {
            service.updateGrade(id, course_id, grade_id, grade);
        } catch (InvalidObjectException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/course/{course_id}/deleteGrade/{grade_id}")
    ResponseEntity<Void> deleteGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                     @PathVariable("grade_id") long grade_id) {


        try {
            service.deleteGrade(id, course_id, grade_id);
        } catch (InvalidObjectException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
