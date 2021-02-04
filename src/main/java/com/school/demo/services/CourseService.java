package com.school.demo.services;

import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.models.CreateCourseModel;

public interface CourseService {


    CourseDTO get(long curseId);

    CourseDTO create(CreateCourseModel model);

    boolean create();

    boolean delete(long id);

    boolean assignTeacher(long courseId, TeacherDTO teacher);

    boolean assignTeacher(long courseId, long teacherId);

    boolean addStudent(long courseId, StudentDTO student);

    boolean addStudent(long courseId, long studentId);

    boolean removeStudent(long courseId, StudentDTO student);

    boolean removeStudent(long courseId, long studentId);
}
