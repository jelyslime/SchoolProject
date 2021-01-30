package com.school.demo.services;

import com.school.demo.dto.*;
import com.school.demo.entity.Course;
import com.school.demo.entity.Director;
import com.school.demo.entity.Teacher;
import com.school.demo.repository.DirectorRepository;
import com.school.demo.views.CourseIdAndGradesView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final ModelMapper mapper;
    private final DirectorRepository directorRepository;

    @Override
    public List<CourseIdAndGradesView> getAllCoursesAndAllGrades(long directorId) {
        DirectorDTO director = convertToDTO(directorRepository.findById(directorId).orElse(new Director()),DirectorDTO.class);
        List<CourseDTO> courses = new ArrayList<>();

        List<TeacherDTO> teacherDTOS = director.getSchool().getTeachers()
                .stream()
                .map(x -> convertToDTO(x,TeacherDTO.class))
                .collect(Collectors.toList());

        List<Set<Course>> setList = teacherDTOS
                .stream()
                .map(TeacherDTO::getCourses)
                .collect(Collectors.toList());

        List<Course> courses1 = new ArrayList<>();
        setList.forEach(courses1::addAll);

        return courses1.stream()
                .map(x -> mapper.map(x,CourseIdAndGradesView.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getAllTeachers(long schoolId) {
        return null;
    }

    @Override
    public List<ParentDTO> getAllParents(long schoolId) {
        return null;
    }

    private <T,S>S convertToDTO(T toBeConverted,Class<S> type){
        return mapper.map(toBeConverted,type);
    }
}
