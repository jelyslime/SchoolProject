package com.school.demo.controllers;

import com.school.demo.models.CreateDirectorModel;
import com.school.demo.services.DirectorServiceImpl;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.DirectorView;
import com.school.demo.views.ParentDirectorView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
@AllArgsConstructor
public class DirectorController {

    DirectorServiceImpl service;
    private final ModelMapper mapper;

    @GetMapping("/{directorId}")
    public DirectorView getDirector(@PathVariable("directorId") long id){
        return mapper.map(service.getDirector(id),DirectorView.class);
    }

    @PostMapping("/create")
    public DirectorView createDirector(@RequestBody CreateDirectorModel model){
        return mapper.map(service.create(model),DirectorView.class);
    }

    @PutMapping("/{directorId}/edit")
    public DirectorView editDirector(@PathVariable("directorId") long id, @RequestBody CreateDirectorModel model){
        return mapper.map(service.edit(id,model),DirectorView.class);
    }

    @DeleteMapping("/{directorId}/delete")
    public ResponseEntity<Void> deleteDirector(@PathVariable("directorId") long id){
        boolean flag = service.delete(id);
        if (flag){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{directorId}/getAllGradesOnSubjects")
    public List<CourseIdAndGradesView> getAllGradesOnSubjects(@PathVariable("directorId") long id) {
        return service.getAllCoursesAndAllGrades(id);
    }

    @GetMapping("/{directorId}/getAllTeachers")
    public List<TeacherView> getAllTeachers(@PathVariable("directorId") long id) {
        return service.getAllTeachers(id);
    }

    @GetMapping("/{directorId}/getAllParents")
    public List<ParentDirectorView> getAllParents(@PathVariable("directorId") long id) {
        return service.getAllParents(id);
    }

}
