package com.school.demo.services.implementations;


import com.school.demo.converter.GenericConverter;
import com.school.demo.data.entity.Director;
import com.school.demo.data.entity.School;
import com.school.demo.data.entity.Student;
import com.school.demo.data.entity.Teacher;
import com.school.demo.data.repository.DirectorRepository;
import com.school.demo.data.repository.SchoolRepository;
import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.DirectorDTO;
import com.school.demo.dto.SchoolDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreateSchoolModel;
import com.school.demo.services.SchoolService;
import com.school.demo.services.TeacherService;
import com.school.demo.validator.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SchoolServiceImpl implements SchoolService {

    private final GenericConverter converter;
    private final Validator validator;

    private final StudentServiceImpl service;
    private final StudentServiceImpl studentService;
    private final TeacherService teacherService;
    private final CourseServiceImpl courseService;

    private final DirectorRepository directorService;
    private final SchoolRepository repository;

    @Override
    public SchoolDTO get(long schoolId) {
        log.info("Calling school repository");
        return converter.convert(repository.findById(schoolId)
                        .orElseThrow(() ->
                                new NoSuchDataException(String.format("School %s does not exists in records.", schoolId)))
                , SchoolDTO.class);
    }

    @Override
    public SchoolDTO create(CreateSchoolModel model) {
        log.debug("Creating new school.");
        validateModel(model);

        SchoolDTO school = populateSchool(new SchoolDTO(), model);

        repository.save(converter.convert(school, School.class));
        return school;
    }


    @Override
    public boolean edit(long id, CreateSchoolModel model) {
        log.debug("Editing school " + id);
        validateModel(model);

        SchoolDTO school = this.get(id);

        school.setAddress(model.getAddress());
        school.setName(model.getName());

        repository.save(converter.convert(school, School.class));
        return true;
    }

    @Override
    public boolean delete(long id) {
        log.debug("Deleting school " + id);
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
        log.debug("Assigning director " + directorID + "to school " + schoolId);
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
        log.debug("Removing director on school " + schoolId);
        SchoolDTO school = this.get(schoolId);

        school.setDirector(null);

        repository.save(converter.convert(school, School.class));
        return true;
    }

    @Override
    public boolean addStudent(long schoolId, long studentId) {
        log.debug("Adding student " + studentId + " to school " + schoolId);
        SchoolDTO school = this.get(schoolId);

        StudentDTO studentDTO = studentService.get(studentId);
        studentDTO.setSchool(converter.convert(school, School.class));

        school.getStudents().add(converter.convert(studentDTO, Student.class));
        repository.saveAndFlush(converter.convert(school, School.class));
        return true;
    }

    @Override
    public boolean removeStudent(long schoolId, long studentId) {
        log.debug("Removing student " + schoolId + " from school " + schoolId);
        SchoolDTO school = this.get(schoolId);


        StudentDTO studentDTO = studentService.get(studentId);
        studentService.removeSchool(studentDTO.getId());

        boolean result = school.getStudents().remove(converter.convert(studentDTO, Student.class));
        repository.save(converter.convert(school, School.class));
        return result;
    }

    @Override
    public boolean assignTeacher(long schoolId, long teacherId) {
        log.debug("Assign teacher " + teacherId + " to school " + schoolId);
        SchoolDTO school = this.get(schoolId);


        TeacherDTO teacherDTO = teacherService.get(teacherId);
        teacherDTO.setSchool(converter.convert(school, School.class));


        school.getTeachers().add(converter.convert(teacherDTO, Teacher.class));
        repository.saveAndFlush(converter.convert(school, School.class));
        return true;
    }


    @Override
    public boolean removeTeacher(long schoolId, long teacherId) {
        log.debug("Removing teacher " + teacherId + " from school " + schoolId);
        SchoolDTO school = this.get(schoolId);

        TeacherDTO teacherDTO = teacherService.get(teacherId);
        teacherService.removeSchool(teacherDTO.getId());

        boolean result = school.getTeachers().remove(converter.convert(teacherDTO, Teacher.class));
        repository.saveAndFlush(converter.convert(school, School.class));
        return result;
    }

    @Override
    public boolean assignStudentToCourse(long schoolId, long courseID, long studentId) {
        log.debug("Assign student " + studentId + " to course " + courseID);
        SchoolDTO school = this.get(schoolId);

        List<CourseDTO> courses = getCourseDTO(school);


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
        log.debug("Removing student " + studentId + " to course " + courseID);
        SchoolDTO school = this.get(schoolId);

        List<CourseDTO> courses = getCourseDTO(school);


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
        log.debug("Getting average grade per student for school " + schoolId);
        SchoolDTO school = this.get(schoolId);

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
        log.debug("Getting filtered average grade per student for school " + schoolId);
        return this.avgGradeOnStudents(schoolId).entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 4.5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Double> avgGradeOnStudentsWhoHaveLessThenFourPointFive(long schoolId) {
        log.debug("Getting filtered average grade per student for school " + schoolId);
        return this.avgGradeOnStudents(schoolId).entrySet()
                .stream()
                .filter(entry -> entry.getValue() < 4.5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<CourseDTO> getCourseDTO(SchoolDTO school) {
        return school.getTeachers()
                .stream()
                .map(Teacher::getCourses)
                .flatMap(Collection::stream)
                .map(course -> converter.convert(course, CourseDTO.class))
                .collect(Collectors.toList());
    }

    private SchoolDTO populateSchool(SchoolDTO school, CreateSchoolModel model) {

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
