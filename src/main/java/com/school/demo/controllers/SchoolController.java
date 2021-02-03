package com.school.demo.controllers;

import com.school.demo.models.CreateSchoolModel;
import com.school.demo.services.SchoolServiceImpl;
import com.school.demo.views.SchoolView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/schools")
@AllArgsConstructor
public class SchoolController {


    private final SchoolServiceImpl service;
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public SchoolView getSchool(@PathVariable long id) {
        return mapper.map(service.get(id), SchoolView.class);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createSchool(@RequestBody CreateSchoolModel model) {
        service.create(model);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Void> editSchool(@PathVariable long id, @RequestBody CreateSchoolModel model) {
        service.edit(id, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteSchool(@PathVariable long id, @RequestBody CreateSchoolModel model) {
        boolean result = service.delete(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //sholud word
    @PutMapping("/{id}/assign/director/{directorId}")
    public ResponseEntity<Void> addDirector(@PathVariable long id, @PathVariable long directorId) {
        service.assignDirector(id, directorId);
        return ResponseEntity.ok().build();
    }

    //prob works
    @PutMapping("/{id}/remove/director/{directorId}")
    public ResponseEntity<Void> removeDirector(@PathVariable long id, @PathVariable long directorId) {
        service.removeDirector(id);
        return ResponseEntity.ok().build();
    }

    //assing should be done trhough student side
    @PutMapping("/{id}/assign/student/{studentId}")
    public ResponseEntity<Void> addStudent(@PathVariable long id, @PathVariable long studentId) {
        service.addStudent(id, studentId);
        return ResponseEntity.ok().build();
    }

    //assing should be done trhough student size
    @PutMapping("/{id}/remove/student/{studentId}")
    public ResponseEntity<Void> removeStudent(@PathVariable long id, @PathVariable long studentId) {
        service.removeStudent(id, studentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/assign/teacher/{teacherId}")
    public ResponseEntity<Void> addTeacher(@PathVariable long id, @PathVariable long teacherId) {
        service.assignTeacher(id, teacherId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/remove/teacher/{teacherId}")
    public ResponseEntity<Void> removeTeacher(@PathVariable long id, @PathVariable long teacherId) {
        service.removeTeacher(id, teacherId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/assign/student/{studentId}/course/{courseId}")
    public ResponseEntity<Void> addStudentToCourse(@PathVariable long id,
                                                   @PathVariable long studentId,
                                                   @PathVariable long courseId) {
        service.assignStudentToCourse(id, courseId, studentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/assign/teacher/{teacherId}/course/{courseId}")
    public ResponseEntity<Void> addTeacherToCourse(@PathVariable long id,
                                                   @PathVariable long teacherId,
                                                   @PathVariable long courseId) {
        service.assignTeacherToCourse(id, teacherId, courseId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/remove/student/{studentId}/course/{courseId}")
    public ResponseEntity<Void> removeStudentFromCourse(@PathVariable long id,
                                                        @PathVariable long studentId,
                                                        @PathVariable long courseId) {
        service.removeStudentFromCourse(id, courseId, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/students/average_grade")
    public Map<String, Double> avgGradeOnStudent(@PathVariable("id") long id) {
        return service.avgGradeOnStudents(id);
    }

    @GetMapping("/{id}/students/average_grade/more_then_4.5")
    public Map<String, Double> avgGradeOnStudentsWhoHaveMoreThenFourPointFive(@PathVariable("id") long id) {
        return service.avgGradeOnStudentsWhoHaveMoreThenFourPointFive(id);
    }

    @GetMapping("/{id}/students/average_grade/less_then_4.5")
    public Map<String, Double> avgGradeOnStudentsWhoHaveLessThenFourPointFive(@PathVariable("id") long id) {
        return service.avgGradeOnStudentsWhoHaveLessThenFourPointFive(id);
    }
}
