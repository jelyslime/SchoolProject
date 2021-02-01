package com.school.demo.services;

import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.DirectorDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.models.CreateDirectorModel;

import java.util.List;

public interface CourseService {

    CourseDTO get(long curseId);

    boolean create();

    boolean delete(long id);

    boolean assignTeacher(long courseId, TeacherDTO teacher);

    boolean addStudent(long courseId, StudentDTO student);

    boolean removeStudent(long courseId, StudentDTO student);
}
