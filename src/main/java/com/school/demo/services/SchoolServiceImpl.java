package com.school.demo.services;

import com.school.demo.converter.GenericConverter;
import com.school.demo.dto.*;
import com.school.demo.entity.Director;
import com.school.demo.entity.School;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreateSchoolModel;
import com.school.demo.repository.DirectorRepository;
import com.school.demo.repository.SchoolRepository;
import com.school.demo.validator.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final GenericConverter converter;
    private final SchoolRepository repository;

    private final StudentServiceImpl service;
    private final DirectorRepository directorService;
    private final StudentServiceImpl studentService;
    private final TeacherService teacherService;
    private final CourseServiceImpl courseService;
    private final Validator validator;

    @Override
    public SchoolDTO get(long schoolId) {
        return converter.convert(repository.findById(schoolId)
                        .orElseThrow(() -> new NoSuchDataException(String.format("School %s does not exists in records.", schoolId)))
                , SchoolDTO.class);
    }

    @Override
    public SchoolDTO create(CreateSchoolModel model) {
        validateModel(model);

        SchoolDTO school = populateSchool(model);

        repository.save(converter.convert(school, School.class));
        return school;
    }



    @Override
    public boolean edit(long id, CreateSchoolModel model) {
        validateModel(model);

        SchoolDTO school = this.get(id);

        school.setAddress(model.getAddress());
        school.setName(model.getName());

        repository.save(converter.convert(school, School.class));
        return true;
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
    public boolean assignDirector(long schoolId, long directorID) {
        SchoolDTO school = this.get(schoolId);

        DirectorDTO director = converter.convert(directorService.findById(directorID)
                        .orElseThrow(() -> new NoSuchDataException(String.format("Director %s does not exists in records.", directorID)))
                , DirectorDTO.class);

        school.setDirector(converter.convert(director, Director.class));
        repository.save(converter.convert(school, School.class));
        return true;
    }

    @Override
    public boolean removeDirector(long schoolId) {
        SchoolDTO school = this.get(schoolId);

        school.setDirector(null);

        repository.save(converter.convert(school, School.class));
        return true;
    }

    @Override
    public boolean addStudent(long schoolId, long studentId) {
        SchoolDTO school = this.get(schoolId);

        StudentDTO studentDTO = studentService.get(studentId);
        studentDTO.setSchool(converter.convert(school, School.class));

        school.getStudents().add(converter.convert(studentDTO, Student.class));
        repository.save(converter.convert(school, School.class));
        return true;
    }

    @Override
    public boolean removeStudent(long schoolId, long studentId) {
        SchoolDTO school = this.get(schoolId);


        StudentDTO studentDTO = studentService.get(studentId);
        studentService.removeSchool(studentDTO.getId());

        boolean result = school.getStudents().remove(converter.convert(studentDTO, Student.class));
        repository.save(converter.convert(school, School.class));
        return result;
    }

    @Override
    public boolean assignTeacher(long schoolId, long teacherId) {
        SchoolDTO school = this.get(schoolId);


        TeacherDTO teacherDTO = teacherService.get(teacherId);
        teacherDTO.setSchool(converter.convert(school, School.class));


        school.getTeachers().add(converter.convert(teacherDTO, Teacher.class));
        repository.saveAndFlush(converter.convert(school, School.class));
        return true;
    }


    @Override
    public boolean removeTeacher(long schoolId, long teacherId) {
        SchoolDTO school = this.get(schoolId);

        TeacherDTO teacherDTO = teacherService.get(teacherId);
        teacherService.removeSchool(teacherDTO.getId());

        boolean result = school.getTeachers().remove(converter.convert(teacherDTO, Teacher.class));
        repository.saveAndFlush(converter.convert(school, School.class));
        return result;
    }

    @Override
    public boolean assignStudentToCourse(long schoolId, long courseID, long studentId) {
        SchoolDTO school = this.get(schoolId);

        List<CourseDTO> courses = school.getTeachers()
                .stream()
                .map(Teacher::getCourses)
                .flatMap(Collection::stream)
                .map(course -> converter.convert(course, CourseDTO.class))
                .collect(Collectors.toList());


        if (!courses.contains(courseService.get(courseID))) {
            throw new NoSuchDataException(String.format("Course %s does not exists in school records.", courseID));
        }
        StudentDTO studentDTO = studentService.get(studentId);
        if (school.getStudents().contains(converter.convert(studentDTO, Student.class))) {
            throw new NoSuchDataException(String.format("Student %s does not exists in school records.", studentId));
        }

        return courseService.addStudent(courseID, studentDTO);
    }

    @Override
    public boolean removeStudentFromCourse(long schoolId, long courseID, long studentId) {
        SchoolDTO school = this.get(schoolId);

        List<CourseDTO> courses = school.getTeachers()
                .stream()
                .map(Teacher::getCourses)
                .flatMap(Collection::stream)
                .map(course -> converter.convert(course, CourseDTO.class))
                .collect(Collectors.toList());


        if (!courses.contains(courseService.get(courseID))) {
            throw new NoSuchDataException(String.format("Course %s does not exists in school records.", courseID));
        }

        StudentDTO studentDTO = studentService.get(studentId);
        if (school.getStudents().contains(converter.convert(studentDTO, Student.class))) {
            throw new NoSuchDataException(String.format("Student %s does not exists in school records.", studentId));
        }

        return courseService.removeStudent(courseID, studentDTO);
    }


    @Override
    public Map<String, Double> avgGradeOnStudents(long schoolId) {
        SchoolDTO school = converter.convert(repository.findById(schoolId).orElse(new School()), SchoolDTO.class);


        List<StudentDTO> students = converter.convertList(school.getStudents(), StudentDTO.class);

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


    private SchoolDTO populateSchool(CreateSchoolModel model) {
        SchoolDTO school = new SchoolDTO();

        school.setAddress(model.getAddress());
        school.setDirector(null);
        school.setName(model.getName());
        school.setStudents(new ArrayList<>());
        school.setTeachers(new ArrayList<>());
        return school;
    }

    private void validateModel(CreateSchoolModel model) {
        validator.validateAddress(model.getAddress());
        validator.validateName(model.getName());
    }
}
