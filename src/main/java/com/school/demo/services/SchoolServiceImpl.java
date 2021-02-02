package com.school.demo.services;

import com.school.demo.dto.*;
import com.school.demo.entity.Director;
import com.school.demo.entity.School;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreateSchoolModel;
import com.school.demo.repository.DirectorRepository;
import com.school.demo.repository.SchoolRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository repository;
    private final ModelMapper mapper;
    private final StudentServiceImpl service;
    private final DirectorRepository directorService;
    private final StudentServiceImpl studentService;
    private final TeacherService teacherService;
    private final CourseServiceImpl courseService;

    @Override
    public SchoolDTO get(long schoolId) {
        return mapper.map(repository.findById(schoolId).orElse(null), SchoolDTO.class);
    }

    @Override
    public SchoolDTO create(CreateSchoolModel model) {
        SchoolDTO school = new SchoolDTO();

        school.setAddress(model.getAddress());
        school.setDirector(new Director());
        school.setName(model.getName());
        school.setStudents(new ArrayList<>());
        school.setTeachers(new ArrayList<>());

        repository.save(mapper.map(school, School.class));
        return school;
    }

    @Override
    public boolean edit(long id, CreateSchoolModel model) {
        SchoolDTO school = this.get(id);

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", id));
        }

        school.setAddress(model.getAddress());
        school.setName(model.getName());

        repository.save(mapper.map(school, School.class));
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

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", schoolId));
        }

        DirectorDTO director = mapper.map(directorService.findById(directorID).orElse(null), DirectorDTO.class);

        if (Objects.isNull(director)) {
            throw new NoSuchDataException(String.format("Director %s does not exists in records.", directorID));
        }

        school.setDirector(mapper.map(director, Director.class));
        repository.save(mapper.map(school, School.class));
        return true;
    }

    @Override
    public boolean removeDirector(long schoolId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", schoolId));
        }

        school.setDirector(new Director());

        repository.save(mapper.map(school, School.class));
        return true;
    }

    @Override
    public boolean addStudent(long schoolId, long studentId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", schoolId));
        }

        StudentDTO studentDTO = studentService.get(studentId);

        if (Objects.isNull(studentDTO)) {
            throw new NoSuchDataException(String.format("Student %s does not exists in records.", studentId));
        }

        school.getStudents().add(mapper.map(studentDTO, Student.class));
        repository.save(mapper.map(school, School.class));
        return true;
    }

    @Override
    public boolean removeStudent(long schoolId, long studentId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", schoolId));
        }

        StudentDTO studentDTO = studentService.get(studentId);

        if (Objects.isNull(studentDTO)) {
            throw new NoSuchDataException(String.format("Student %s does not exists in records.", studentId));
        }

        boolean result = school.getStudents().remove(mapper.map(studentDTO, Student.class));
        repository.save(mapper.map(school, School.class));
        return result;
    }

    @Override
    public boolean assignTeacher(long schoolId, long teacherId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", schoolId));
        }

        TeacherDTO teacherDTO = teacherService.get(teacherId);

        if (Objects.isNull(teacherDTO)) {
            throw new NoSuchDataException(String.format("Teacher %s does not exists in records.", teacherId));
        }

        school.getTeachers().add(mapper.map(teacherDTO, Teacher.class));
        repository.save(mapper.map(school, School.class));
        return true;
    }

    @Override
    public boolean removeTeacher(long schoolId, long teacherId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", schoolId));
        }

        TeacherDTO teacherDTO = teacherService.get(teacherId);

        if (Objects.isNull(teacherDTO)) {
            throw new NoSuchDataException(String.format("Teacher %s does not exists in records.", teacherId));
        }

        boolean result = school.getTeachers().remove(mapper.map(teacherDTO, Teacher.class));
        repository.save(mapper.map(school, School.class));
        return result;
    }

    @Override
    public boolean assignStudentToCourse(long schoolId, long courseID, long studentId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", schoolId));
        }

        List<CourseDTO> courses = school.getTeachers()
                .stream()
                .map(Teacher::getCourses)
                .flatMap(Collection::stream)
                .map(course -> mapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());


        if (!courses.contains(courseService.get(courseID))) {
            throw new NoSuchDataException(String.format("Course %s does not exists in school records.", courseID));
        }
        StudentDTO studentDTO = studentService.get(studentId);
        if (school.getStudents().contains(mapper.map(studentDTO, Student.class))) {
            throw new NoSuchDataException(String.format("Student %s does not exists in school records.", studentId));
        }

        return courseService.addStudent(courseID, studentDTO);
    }

    @Override
    public boolean removeStudentFromCourse(long schoolId, long courseID, long studentId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", schoolId));
        }

        List<CourseDTO> courses = school.getTeachers()
                .stream()
                .map(Teacher::getCourses)
                .flatMap(Collection::stream)
                .map(course -> mapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());


        if (!courses.contains(courseService.get(courseID))) {
            throw new NoSuchDataException(String.format("Course %s does not exists in school records.", courseID));
        }
        StudentDTO studentDTO = studentService.get(studentId);
        if (school.getStudents().contains(mapper.map(studentDTO, Student.class))) {
            throw new NoSuchDataException(String.format("Student %s does not exists in school records.", studentId));
        }

        return courseService.removeStudent(courseID, studentDTO);
    }


    @Override
    public Map<String, Double> avgGradeOnStudents(long schoolId) {
        SchoolDTO school = mapper.map(repository.findById(schoolId).orElse(new School()), SchoolDTO.class);

        if (Objects.isNull(school)) {
            throw new NoSuchDataException(String.format("School %s does not exists in records.", schoolId));
        }

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
