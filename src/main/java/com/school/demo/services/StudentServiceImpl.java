package com.school.demo.services;

import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.GradeDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.entity.Course;
import com.school.demo.entity.Grade;
import com.school.demo.entity.Role;
import com.school.demo.entity.School;
import com.school.demo.entity.Student;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreatePerson;
import com.school.demo.repository.StudentRepository;
import com.school.demo.validator.Validator;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.SimpleGradeView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final ModelMapper mapper;
    private final Validator validator;

    @Override
    public StudentDTO get(long studentId) {
        return mapper.map(repository.findById(studentId)
                        .orElseThrow(() -> new NoSuchDataException(String.format("Student %s does not exists in records.", studentId)))
                , StudentDTO.class);
    }

    @Override
    public StudentDTO create(CreatePerson model) {
        Role role = Role.STUDENT;
        validator.validateRole(role);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setCourses(new HashSet<>());
        studentDTO.setGrades(new HashSet<>());
        studentDTO.setSchool(new School());
        studentDTO.setParents(new HashSet<>());

        studentDTO.setFirstName(model.getFirstName());
        studentDTO.setLastName(model.getLastName());
        studentDTO.setUsername(model.getUsername());
        studentDTO.setPassword(model.getPassword());

        Student entity = mapper.map(studentDTO,Student.class);
        return mapper.map(repository.save(entity),StudentDTO.class);

    }

    @Override
    public StudentDTO edit(long id, CreatePerson model) {
        Role role = Role.STUDENT;
        validator.validateRole(role);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setFirstName(model.getFirstName());
        studentDTO.setLastName(model.getLastName());
        studentDTO.setUsername(model.getUsername());
        studentDTO.setPassword(model.getPassword());
        studentDTO.setId(id);

        Student entity = mapper.map(studentDTO,Student.class);
        return mapper.map(repository.save(entity),StudentDTO.class);

    }

    @Override
    public boolean delete(long id) {
        boolean result = repository.existsById(id);
        if (!result) {
            return false;
        }
        repository.deleteById(id);
        result = repository.existsById(id);
        return !result;
    }


    @Override
    public List<CourseIdAndGradesView> getAllGrades(long studentId) {
        StudentDTO student = this.get(studentId);

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
        StudentDTO student = this.get(studentId);

        return student.getCourses()
                .stream()
                .map(Course::getTeacher)
                .map(teacher -> mapper.map(teacher, TeacherView.class))
                .collect(Collectors.toList());
    }

    @Override
    public double getAvgGrade(long studentId) {
        StudentDTO student = this.get(studentId);

        return student.getGrades()
                .stream()
                .mapToDouble(Grade::getGrade)
                .average().orElse(2.0);
    }


}
