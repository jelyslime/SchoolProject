package com.school.demo.services;

import com.school.demo.converter.GenericConverter;
import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.entity.Course;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreateCourseModel;
import com.school.demo.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final GenericConverter converter;
    private final ModelMapper mapper;
    private final CourseRepository courseRepository;
    private final TeacherServiceImpl service;
    private final StudentServiceImpl studentService;

    @Override
    public CourseDTO get(long curseId) {
        return mapper.map(courseRepository.findById(curseId)
                        .orElseThrow(() -> new NoSuchDataException(String.format("Curse %s does not exists in records.", curseId)))
                , CourseDTO.class);
    }

    @Override
    public CourseDTO create(CreateCourseModel model) {
        CourseDTO course = populateCourse(model);

        Course entity = mapper.map(course, Course.class);
        entity = courseRepository.save(entity);

        return converter.convert(entity, CourseDTO.class);
    }

    private CourseDTO populateCourse(CreateCourseModel model) {
        CourseDTO course = new CourseDTO();
        course.setGrades(new HashSet<>());
        course.setStudents(new HashSet<>());
        course.setTeacher(converter.convert(service.get(model.getTeacherId()), Teacher.class));
        return course;
    }


    @Override
    public boolean create() {
        CourseDTO course = new CourseDTO();
        course.setGrades(new HashSet<>());
        course.setStudents(new HashSet<>());
        course.setTeacher(new Teacher());

        courseRepository.save(mapper.map(course, Course.class));

        return true;
    }

    @Override
    public boolean delete(long id) {
        boolean result = courseRepository.existsById(id);
        if (!result) {
            return false;
        }
        courseRepository.deleteById(id);
        result = courseRepository.existsById(id);
        return !result;
    }

    @Override
    public boolean assignTeacher(long courseId, TeacherDTO teacher) {
        CourseDTO courseDTO = this.get(courseId);

        courseDTO.setTeacher(mapper.map(teacher, Teacher.class));

        courseRepository.save(mapper.map(courseDTO, Course.class));

        return true;
    }

    @Override
    public boolean assignTeacher(long courseId, long teacherId) {
        TeacherDTO teacher = service.get(teacherId);
        CourseDTO course = this.get(courseId);

        course.setTeacher(mapper.map(teacher, Teacher.class));

        courseRepository.saveAndFlush(mapper.map(course, Course.class));
        return true;
    }

    @Override
    public boolean addStudent(long courseId, StudentDTO student) {
        CourseDTO courseDTO = this.get(courseId);


        courseDTO.getStudents().add((mapper.map(student, Student.class)));

        courseRepository.saveAndFlush(mapper.map(courseDTO, Course.class));

        return true;
    }

    @Override
    public boolean addStudent(long courseId, long studentId) {
        return this.addStudent(courseId, studentService.get(studentId));
    }


    @Override
    public boolean removeStudent(long courseId, StudentDTO student) {
        CourseDTO courseDTO = this.get(courseId);

        Set<Student> studentSet = courseDTO.getStudents().stream()
                .filter(student1 -> student1.getId() != student.getId())
                .collect(Collectors.toSet());

        courseDTO.setStudents(studentSet);

        courseRepository.saveAndFlush(mapper.map(courseDTO, Course.class));

        return true;
    }

    @Override
    public boolean removeStudent(long courseId, long studentId) {
        return this.removeStudent(courseId, studentService.get(studentId));
    }


}
