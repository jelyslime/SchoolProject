package com.school.demo.services;

import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.GradeDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.entity.Course;
import com.school.demo.entity.Grade;
import com.school.demo.entity.Student;
import com.school.demo.repository.StudentRepository;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.SimpleGradeView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<CourseIdAndGradesView> getAllGrades(long studentId) {
        StudentDTO student = mapper.map(repository.findById(studentId).orElse(new Student()), StudentDTO.class);

        Set<GradeDTO> grades = student.getGrades()
                .stream()
                .map(x -> mapper.map(x, GradeDTO.class))
                .collect(Collectors.toSet());

        Set<CourseDTO> courseDTOS = student.getCourses()
                .stream()
                .map(x -> mapper.map(x, CourseDTO.class))
                .collect(Collectors.toSet());

        List<CourseIdAndGradesView> courseIdAndGradesViews = new ArrayList<>();

        for (CourseDTO courseDTO : courseDTOS) {
            CourseIdAndGradesView view = new CourseIdAndGradesView();
            view.setId(courseDTO.getId());
            view.setGrades(grades
                    .stream()
                    .filter(x -> x.getCourse().getId() == courseDTO.getId())
                    .map(x -> mapper.map(x, SimpleGradeView.class))
                    .collect(Collectors.toList()));
            courseIdAndGradesViews.add(view);
        }

        return courseIdAndGradesViews;
    }

    @Override
    public List<TeacherView> getAllTeachers(long studentId) {
        StudentDTO student = mapper.map(repository.findById(studentId).orElse(new Student()), StudentDTO.class);

        return student.getCourses()
                .stream()
                .map(Course::getTeacher)
                .map(teacher -> mapper.map(teacher, TeacherView.class))
                .collect(Collectors.toList());
    }

    @Override
    public double getAvgGrade(long studentId) {
        StudentDTO student = mapper.map(repository.findById(studentId).orElse(new Student()), StudentDTO.class);

        return student.getGrades()
                .stream()
                .mapToDouble(Grade::getGrade)
                .average().orElse(2.0);
    }


}
