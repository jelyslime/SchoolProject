package com.school.demo.controllers;

import com.school.demo.services.TeacherServiceImpl;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
public class TeacherController {

    TeacherServiceImpl service;

    //TODO: IMPLEMENT CRUD
    @GetMapping("/{id}/get/all/students/grades")
    Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getAllStudentGradesByTeacherId(@PathVariable("id") long id) {
        return service.getAllStudentGrades(id);
    }

    @PostMapping("/{id}/course/{course_id}/addGrade/{grade}/toStudent/{student_id}")
    ResponseEntity<Void> addGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                  @PathVariable("grade") double grade, @PathVariable("student_id") long student_id) {


        service.addGrade(id, course_id, grade, student_id);


        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/course/{course_id}/gradeId/{grade_id}/update/{grade}")
    ResponseEntity<Void> updateGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                     @PathVariable("grade_id") long grade_id, @PathVariable("grade") double grade) {


        service.updateGrade(id, course_id, grade_id, grade);


        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/course/{course_id}/gradeId/{gradeId}/delete")
    ResponseEntity<Void> deleteGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                     @PathVariable("grade_id") long grade_id) {

        service.deleteGrade(id, course_id, grade_id);


        return ResponseEntity.ok().build();
    }
}
