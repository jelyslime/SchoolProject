package com.school.demo.services;

import com.school.demo.dto.ParentDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.entity.Parent;
import com.school.demo.repository.ParentRepository;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParentServiceImpl implements ParentService {
    private final ParentRepository repository;
    private final ModelMapper mapper;
    private final StudentServiceImpl service;


    @Override
    public Map<String, List<CourseIdAndGradesView>> getAllGrades(long parentId) {
        ParentDTO parentDTO = mapper.map(repository.findById(parentId).orElse(new Parent()), ParentDTO.class);

        List<StudentDTO> kids = parentDTO.getKids()
                .stream()
                .map(student -> mapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());

        Map<String, List<CourseIdAndGradesView>> kidsGrades = new HashMap<>();

        for (StudentDTO kid : kids) {
            kidsGrades.put(String.format("%s %s", kid.getFirstName(), kid.getLastName()), service.getAllGrades(kid.getId()));
        }

        return kidsGrades;
    }

    @Override
    public Map<String, List<TeacherView>> getAllTeachers(long parentId) {
        ParentDTO parentDTO = mapper.map(repository.findById(parentId).orElse(new Parent()), ParentDTO.class);

        List<StudentDTO> kids = parentDTO.getKids()
                .stream()
                .map(student -> mapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());

        Map<String, List<TeacherView>> kidsGrades = new HashMap<>();

        for (StudentDTO kid : kids) {
            kidsGrades.put(String.format("%s %s", kid.getFirstName(), kid.getLastName()), service.getAllTeachers(kid.getId()));
        }

        return kidsGrades;
    }
}
