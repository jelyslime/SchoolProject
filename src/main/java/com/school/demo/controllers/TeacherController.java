package com.school.demo.controllers;

import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.TeacherServiceImpl;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import com.school.demo.views.StudentView;
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

    TeacherServiceImpl service;
    private final ModelMapper mapper;

    //work
    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherView> getTeacher(@PathVariable("teacherId") long id) {
        return ResponseEntity.ok().body(mapper.map(service.get(id), TeacherView.class));
    }

    //work
    @PostMapping("/create")
        public ResponseEntity<TeacherView> createTeacher(@RequestBody CreatePersonModel model) {

        return ResponseEntity.ok().body(mapper.map(service.create(model), TeacherView.class));
    }

    //work
    @PutMapping("/{teacherId}/edit")
    public ResponseEntity<TeacherView> editTeacher(@PathVariable("teacherId") long id, @RequestBody CreatePersonModel model) {

        return ResponseEntity.ok().body(mapper.map(service.edit(id, model), TeacherView.class));
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
