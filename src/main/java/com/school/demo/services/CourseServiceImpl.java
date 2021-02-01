package com.school.demo.services;

import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.entity.Course;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import com.school.demo.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final ModelMapper mapper;
    private final CourseRepository courseRepository;

    @Override
    public CourseDTO get(long curseId) {
        return mapper.map(courseRepository.findById(curseId).orElse(null),CourseDTO.class);
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

        if (Objects.isNull(courseDTO)){
            return false;
        }

        courseDTO.setTeacher(mapper.map(teacher,Teacher.class));

        courseRepository.save(mapper.map(courseDTO,Course.class));

        return true;
    }

    @Override
    public boolean addStudent(long courseId, StudentDTO student) {
        CourseDTO courseDTO = this.get(courseId);

        if (Objects.isNull(courseDTO)){
            return false;
        }

        courseDTO.getStudents().add((mapper.map(student, Student.class)));

        courseRepository.save(mapper.map(courseDTO,Course.class));

        return true;
    }

    @Override
    public boolean removeStudent(long courseId, StudentDTO student) {
        CourseDTO courseDTO = this.get(courseId);

        if (Objects.isNull(courseDTO)){
            return false;
        }

        courseDTO.getStudents().remove((mapper.map(student, Student.class)));

        courseRepository.save(mapper.map(courseDTO,Course.class));

        return true;
    }


}
