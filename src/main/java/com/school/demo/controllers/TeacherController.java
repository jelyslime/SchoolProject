package com.school.demo.controllers;

import com.school.demo.converter.GenericConverter;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.implementations.TeacherServiceImpl;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.GradeView;
import com.school.demo.views.PersonNamesView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
@Slf4j
public class TeacherController {

    private final TeacherServiceImpl service;
    private final GenericConverter converter;

    //work
    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherView> getTeacher(@PathVariable("teacherId") long id) {
        log.info("Endpoint '/{teacherId}' reached.");

        log.debug("Calling service.");
        TeacherView view = converter.convert(service.get(id), TeacherView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PostMapping("/create")
    public ResponseEntity<TeacherView> createTeacher(@RequestBody CreatePersonModel model) {
        log.info("Endpoint '/create' reached.");

        log.debug("Calling service.");
        TeacherView view = converter.convert(service.create(model), TeacherView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PutMapping("/{teacherId}/edit")
    public ResponseEntity<TeacherView> editTeacher(@PathVariable("teacherId") long id, @RequestBody CreatePersonModel model) {
        log.info("Endpoint '/{teacherId}/edit' reached.");

        log.debug("Calling service.");
        TeacherView view = converter.convert(service.edit(id, model), TeacherView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @DeleteMapping("/{teacherId}/delete")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("teacherId") long id) {
        log.info("Endpoint '/{teacherId}/delete' reached.");

        log.debug("Calling service.");
        boolean flag = service.delete(id);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/get/all/students/grades")
    Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getAllStudentGradesByTeacherId(@PathVariable("id") long id) {
        log.info("Endpoint '/{id}/get/all/students/grades' reached.");

        log.debug("Calling service.");
        return service.getAllStudentGrades(id);
    }

    @PostMapping("/{id}/course/{course_id}/add/grade/{grade}/student/{student_id}")
    ResponseEntity<GradeView> addGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                       @PathVariable("grade") double grade, @PathVariable("student_id") long student_id) {


        log.info("Endpoint '/{id}/course/{course_id}/add/grade/{grade}/student/{student_id}' reached.");

        log.debug("Calling service.");
        GradeView view = converter.convert(service.addGrade(id, course_id, grade, student_id), GradeView.class);

        return ResponseEntity.ok().body(view);
    }

    @PutMapping("/{id}/course/{course_id}/edit/grade/{grade_id}/value/{grade}")
    ResponseEntity<GradeView> updateGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                          @PathVariable("grade_id") long grade_id, @PathVariable("grade") double grade) {


        log.info("Endpoint '/{id}/course/{course_id}/edit/grade/{grade_id}/value/{grade}' reached.");

        log.debug("Calling service.");
        GradeView view = converter.convert(service.updateGrade(id, course_id, grade_id, grade), GradeView.class);


        return ResponseEntity.ok().body(view);
    }

    @DeleteMapping("/{id}/course/{course_id}/delete/grade/{gradeId}")
    ResponseEntity<Void> deleteGrade(@PathVariable("id") long id, @PathVariable("course_id") long course_id,
                                     @PathVariable("gradeId") long grade_id) {

        log.info("Endpoint '/{id}/course/{course_id}/delete/grade/{gradeId}' reached.");

        log.debug("Calling service.");
        service.deleteGrade(id, course_id, grade_id);


        return ResponseEntity.ok().build();
    }
}
