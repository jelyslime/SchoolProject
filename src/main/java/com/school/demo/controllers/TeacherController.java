package com.school.demo.controllers;

import com.school.demo.converter.GenericConverter;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.implementations.TeacherServiceImpl;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.GradeView;
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

    private final ModelMapper mapper;
    private final TeacherServiceImpl service;
    private final GenericConverter converter;
    //work
    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherView> getTeacher(@PathVariable("teacherId") long id) {
        TeacherView view = converter.convert(service.get(id),TeacherView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PostMapping("/create")
    public ResponseEntity<TeacherView> createTeacher(@RequestBody CreatePersonModel model) {
        TeacherView view = converter.convert(service.create(model),TeacherView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PutMapping("/{teacherId}/edit")
    public ResponseEntity<TeacherView> editTeacher(@PathVariable("teacherId") long id, @RequestBody CreatePersonModel model) {
        TeacherView view = converter.convert(service.edit(id, model),TeacherView.class);

        return ResponseEntity.ok().body(view);
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

    @PostMapping("/{id}/course/{course_id}/add/grade/{grade}/student/{student_id}")
    ResponseEntity<GradeView> addGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                       @PathVariable("grade") double grade, @PathVariable("student_id") long student_id) {


        GradeView view = converter.convert(service.addGrade(id, course_id, grade, student_id),GradeView.class);

        return ResponseEntity.ok().body(view);
    }

    @PutMapping("/{id}/course/{course_id}/edit/grade/{grade_id}/value/{grade}")
    ResponseEntity<GradeView> updateGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                     @PathVariable("grade_id") long grade_id, @PathVariable("grade") double grade) {


        GradeView view = converter.convert(service.updateGrade(id, course_id, grade_id, grade),GradeView.class);


        return ResponseEntity.ok().body(view);
    }

    @DeleteMapping("/{id}/course/{course_id}/delete/grade/{gradeId}")
    ResponseEntity<Void> deleteGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                     @PathVariable("gradeId") long grade_id) {

        service.deleteGrade(id, course_id, grade_id);


        return ResponseEntity.ok().build();
    }
}
