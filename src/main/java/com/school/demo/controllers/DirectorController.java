package com.school.demo.controllers;

import com.school.demo.converter.GenericConverter;
import com.school.demo.models.CreateDirectorModel;
import com.school.demo.services.implementations.DirectorServiceImpl;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.DirectorView;
import com.school.demo.views.ParentDirectorView;
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

@RestController
@RequestMapping("/api/directors")
@AllArgsConstructor
@Slf4j
public class DirectorController {

    private final DirectorServiceImpl service;
    private final GenericConverter converter;

    //work
    @GetMapping("/{directorId}")
    public ResponseEntity<DirectorView> getDirector(@PathVariable("directorId") long id) {
        log.info("Endpoint '/{directorId}' reached.");

        log.debug("Calling service.");
        DirectorView view = converter.convert(service.get(id), DirectorView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PostMapping("/create")
    public ResponseEntity<DirectorView> createDirector(@RequestBody CreateDirectorModel model) {
        log.info("Endpoint '/create' reached.");

        log.debug("Calling service.");
        DirectorView view = converter.convert(service.create(model), DirectorView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @PutMapping("/{directorId}/edit")
    public ResponseEntity<DirectorView> editDirector(@PathVariable("directorId") long id, @RequestBody CreateDirectorModel model) {
        log.info("Endpoint '/{directorId}/edit' reached.");

        log.debug("Calling service.");
        DirectorView view = converter.convert(service.edit(id, model), DirectorView.class);

        return ResponseEntity.ok().body(view);
    }

    //work
    @DeleteMapping("/{directorId}/delete")
    public ResponseEntity<Void> deleteDirector(@PathVariable("directorId") long id) {
        log.info("Endpoint '/{directorId}/delete' reached.");

        log.debug("Calling service.");
        boolean flag = service.delete(id);
        if (flag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //work
    @GetMapping("/{directorId}/get/all/grades")
    public List<CourseIdAndGradesView> getAllGradesOnSubjects(@PathVariable("directorId") long id) {
        log.info("Endpoint '/{directorId}/get/all/grades' reached.");

        log.debug("Calling service.");
        return service.getAllCoursesAndAllGrades(id);
    }

    //work
    @GetMapping("/{directorId}/get/all/teachers")
    public List<TeacherView> getAllTeachers(@PathVariable("directorId") long id) {
        log.info("Endpoint '/{directorId}/get/all/teachers' reached.");

        log.debug("Calling service.");
        return service.getAllTeachers(id);
    }

    //work
    @GetMapping("/{directorId}/get/all/parents")
    public List<ParentDirectorView> getAllParents(@PathVariable("directorId") long id) {
        log.info("Endpoint '/{directorId}/get/all/parents' reached.");

        log.debug("Calling service.");
        return service.getAllParents(id);
    }

}
