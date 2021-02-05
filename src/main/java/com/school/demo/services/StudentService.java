package com.school.demo.services;

import com.school.demo.dto.StudentDTO;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;

import java.util.List;


public interface StudentService {


    StudentDTO get(long studentId);

    StudentDTO create(CreatePersonModel model);

    StudentDTO edit(long id, CreatePersonModel model);

    boolean delete(long id);

    boolean removeSchool(long id);

    List<CourseIdAndGradesView> getAllGrades(long studentId);

    List<TeacherView> getAllTeachers(long studentId);

    double getAvgGrade(long studentId);
}
