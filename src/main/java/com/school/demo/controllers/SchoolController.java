package com.school.demo.controllers;

import com.school.demo.services.SchoolServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/schools")
@AllArgsConstructor
public class SchoolController {

    private final SchoolServiceImpl service;

    @GetMapping("/{id}/studentsAverageGrade")
    public Map<String, Double> avgGradeOnStudent(@PathVariable("id") long id) {
        return service.avgGradeOnStudents(id);
    }

    @GetMapping("/{id}/studentsAverageGrade_More_Then_4.5")
    public Map<String, Double> avgGradeOnStudentsWhoHaveMoreThenFourPointFive(@PathVariable("id") long id) {
        return service.avgGradeOnStudentsWhoHaveMoreThenFourPointFive(id);
    }

    @GetMapping("/{id}/studentsAverageGrade_Less_Then_4.5")
    public Map<String, Double> avgGradeOnStudentsWhoHaveLessThenFourPointFive(@PathVariable("id") long id) {
        return service.avgGradeOnStudentsWhoHaveLessThenFourPointFive(id);
    }
}
