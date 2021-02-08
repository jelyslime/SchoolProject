package com.school.demo.controllers;

import com.school.demo.converter.GenericConverter;
import com.school.demo.models.CreateSchoolModel;
import com.school.demo.services.implementations.SchoolServiceImpl;
import com.school.demo.views.SchoolView;
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

import java.util.Map;

@RestController
@RequestMapping("/api/schools")
@AllArgsConstructor
@Slf4j
public class SchoolController {

    private final GenericConverter converter;
    private final SchoolServiceImpl service;


    @GetMapping("/{id}")
    public ResponseEntity<SchoolView> getSchool(@PathVariable long id) {
        log.info("Endpoint '/{id}' reached.");

        log.debug("Calling service.");
        SchoolView view = converter.convert(service.get(id), SchoolView.class);

        return ResponseEntity.ok().body(view);
    }

    @PostMapping("/create")
    public ResponseEntity<SchoolView> createSchool(@RequestBody CreateSchoolModel model) {
        log.info("Endpoint '/create' reached.");

        log.debug("Calling service.");
        SchoolView view = converter.convert(service.create(model), SchoolView.class);

        return ResponseEntity.ok().body(view);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Void> editSchool(@PathVariable long id, @RequestBody CreateSchoolModel model) {
        log.info("Endpoint '/{id}/edit' reached.");

        log.debug("Calling service.");
        service.edit(id, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteSchool(@PathVariable long id) {
        log.info("Endpoint '/{id}/delete' reached.");

        log.debug("Calling service.");
        boolean result = service.delete(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}/assign/director/{directorId}")
    public ResponseEntity<Void> addDirector(@PathVariable long id, @PathVariable long directorId) {
        log.info("Endpoint '/{id}/assign/director/{directorId}' reached.");

        log.debug("Calling service.");
        service.assignDirector(id, directorId);
        return ResponseEntity.ok().build();
    }

    //prob works
    @PutMapping("/{id}/remove/director/{directorId}")
    public ResponseEntity<Void> removeDirector(@PathVariable long id, @PathVariable long directorId) {
        log.info("Endpoint '/{id}/remove/director/{directorId}' reached.");

        log.debug("Calling service.");
        service.removeDirector(id);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/assign/student/{studentId}")
    public ResponseEntity<Void> addStudent(@PathVariable long id, @PathVariable long studentId) {
        log.info("Endpoint '/{id}/assign/student/{studentId}' reached.");

        log.debug("Calling service.");
        service.addStudent(id, studentId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/remove/student/{studentId}")
    public ResponseEntity<Void> removeStudent(@PathVariable long id, @PathVariable long studentId) {
        log.info("Endpoint '/{id}/remove/student/{studentId}' reached.");

        log.debug("Calling service.");
        service.removeStudent(id, studentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/assign/teacher/{teacherId}")
    public ResponseEntity<Void> addTeacher(@PathVariable long id, @PathVariable long teacherId) {
        log.info("Endpoint '/{id}/assign/teacher/{teacherId}' reached.");

        log.debug("Calling service.");
        service.assignTeacher(id, teacherId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/remove/teacher/{teacherId}")
    public ResponseEntity<Void> removeTeacher(@PathVariable long id, @PathVariable long teacherId) {
        log.info("Endpoint '/{id}/remove/teacher/{teacherId}' reached.");

        log.debug("Calling service.");
        service.removeTeacher(id, teacherId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/assign/student/{studentId}/course/{courseId}")
    public ResponseEntity<Void> addStudentToCourse(@PathVariable long id,
                                                   @PathVariable long studentId,
                                                   @PathVariable long courseId) {
        log.info("Endpoint '/{id}/assign/student/{studentId}/course/{courseId}' reached.");

        log.debug("Calling service.");
        service.assignStudentToCourse(id, courseId, studentId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/remove/student/{studentId}/course/{courseId}")
    public ResponseEntity<Void> removeStudentFromCourse(@PathVariable long id,
                                                        @PathVariable long studentId,
                                                        @PathVariable long courseId) {
        service.removeStudentFromCourse(id, courseId, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/students/average/grade")
    public Map<String, Double> avgGradeOnStudent(@PathVariable("id") long id) {
        return service.avgGradeOnStudents(id);
    }

    @GetMapping("/{id}/students/average/grade/more_then_4.5")
    public Map<String, Double> avgGradeOnStudentsWhoHaveMoreThenFourPointFive(@PathVariable("id") long id) {
        return service.avgGradeOnStudentsWhoHaveMoreThenFourPointFive(id);
    }

    @GetMapping("/{id}/students/average/grade/less_then_4.5")
    public Map<String, Double> avgGradeOnStudentsWhoHaveLessThenFourPointFive(@PathVariable("id") long id) {
        return service.avgGradeOnStudentsWhoHaveLessThenFourPointFive(id);
    }
}
