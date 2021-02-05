package com.school.demo.controllers;

import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.implementations.TeacherServiceImpl;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
public class TeacherController {

    private final ModelMapper mapper;
    TeacherServiceImpl service;

    //work
    @GetMapping("/{teacherId}")
    public TeacherView getTeacher(@PathVariable("teacherId") long id) {
        return mapper.map(service.get(id), TeacherView.class);
    }

    //work
    @PostMapping("/create")
    public ResponseEntity<Void> createTeacher(@RequestBody CreatePersonModel model) {
        service.create(model);
        return ResponseEntity.ok().build();
    }

    //work
    @PutMapping("/{teacherId}/edit")
    public ResponseEntity<Void> editTeacher(@PathVariable("teacherId") long id, @RequestBody CreatePersonModel model) {
        service.edit(id, model);
        return ResponseEntity.ok().build();
    }

    //work
    @DeleteMapping("/{teacherId}/delete")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("teacherId") long id) {
        boolean flag = service.delete(id);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

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
