package com.school.demo.services;

import com.school.demo.dto.SchoolDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.entity.School;
import com.school.demo.repository.SchoolRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository repository;
    private final ModelMapper mapper;
    private final StudentServiceImpl service;


    @Override
    public SchoolDTO get(long schoolId) {
        return mapper.map(repository.findById(schoolId).orElse(new School()),SchoolDTO.class);
    }

    @Override
    public Map<String, Double> avgGradeOnStudents(long schoolId) {
        SchoolDTO school = mapper.map(repository.findById(schoolId).orElse(new School()), SchoolDTO.class);

        List<StudentDTO> students = school.getStudents()
                .stream()
                .map(student -> mapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());

        Map<String, Double> studentsAvgGrades = new HashMap<>();

        for (StudentDTO student : students) {
            studentsAvgGrades.put(String.format("%s %s", student.getFirstName(), student.getLastName()),
                    service.getAvgGrade(student.getId()));
        }

        return studentsAvgGrades;
    }

    @Override
    public Map<String, Double> avgGradeOnStudentsWhoHaveMoreThenFourPointFive(long schoolId) {
        return this.avgGradeOnStudents(schoolId).entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 4.5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Double> avgGradeOnStudentsWhoHaveLessThenFourPointFive(long schoolId) {
        return this.avgGradeOnStudents(schoolId).entrySet()
                .stream()
                .filter(entry -> entry.getValue() < 4.5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
