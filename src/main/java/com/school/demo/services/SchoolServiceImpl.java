package com.school.demo.services;

import com.school.demo.dto.DirectorDTO;
import com.school.demo.dto.SchoolDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.entity.Director;
import com.school.demo.entity.School;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import com.school.demo.models.CreateSchoolModel;
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
    private final DirectorServiceImpl directorService;
    private final StudentServiceImpl studentService;
    private final TeacherService teacherService;

    @Override
    public SchoolDTO get(long schoolId) {
        return mapper.map(repository.findById(schoolId).orElse(null),SchoolDTO.class);
    }

    @Override
    public SchoolDTO create(CreateSchoolModel model) {
        SchoolDTO school = new SchoolDTO();

        school.setAddress(model.getAddress());
        school.setDirector(new Director());
        school.setName(model.getName());
        school.setStudents(new ArrayList<>());
        school.setTeachers(new ArrayList<>());

        repository.save(mapper.map(school,School.class));
        return school;
    }

    @Override
    public boolean edit(long id, CreateSchoolModel model) {
        SchoolDTO school = this.get(id);

        if (Objects.isNull(school)){
            return false;
        }

        school.setAddress(model.getAddress());
        school.setName(model.getName());

        repository.save(mapper.map(school,School.class));
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

        if (Objects.isNull(school)){
            return false;
        }

        DirectorDTO director = directorService.get(directorID);

        if (Objects.isNull(director)){
            return false;
        }

        school.setDirector(mapper.map(director,Director.class));
        repository.save(mapper.map(school,School.class));
        return true;
    }

    @Override
    public boolean removeDirector(long schoolId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)){
            return false;
        }

        school.setDirector(new Director());

        repository.save(mapper.map(school,School.class));
        return true;
    }

    @Override
    public boolean addStudent(long schoolId, long studentId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)){
            return false;
        }

        StudentDTO studentDTO = studentService.get(studentId);

        if (Objects.isNull(studentDTO)){
            return false;
        }

        school.getStudents().add(mapper.map(studentDTO, Student.class));
        repository.save(mapper.map(school,School.class));
        return true;
    }

    @Override
    public boolean removeStudent(long schoolId, long studentId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)){
            return false;
        }

        StudentDTO studentDTO = studentService.get(studentId);

        if (Objects.isNull(studentDTO)){
            return false;
        }

        school.getStudents().remove(mapper.map(studentDTO, Student.class));
        repository.save(mapper.map(school,School.class));
        return true;
    }

    @Override
    public boolean assignTeacher(long schoolId, long teacherId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)){
            return false;
        }

        TeacherDTO teacherDTO = teacherService.get(teacherId);

        if (Objects.isNull(teacherDTO)){
            return false;
        }

        school.getTeachers().add(mapper.map(teacherDTO, Teacher.class));
        repository.save(mapper.map(school,School.class));
        return true;
    }

    @Override
    public boolean removeTeacher(long schoolId, long teacherId) {
        SchoolDTO school = this.get(schoolId);

        if (Objects.isNull(school)){
            return false;
        }

        TeacherDTO teacherDTO = teacherService.get(teacherId);

        if (Objects.isNull(teacherDTO)){
            return false;
        }

        school.getTeachers().remove(mapper.map(teacherDTO, Teacher.class));
        repository.save(mapper.map(school,School.class));
        return true;
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
