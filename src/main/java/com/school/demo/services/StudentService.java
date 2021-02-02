package com.school.demo.services;

import com.school.demo.dto.ParentDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.models.CreatePerson;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;

import java.util.List;


public interface StudentService {

    //TODO CREATE CRUD
    StudentDTO get(long studentId);

    StudentDTO create(CreatePerson model);

    StudentDTO edit(long id, CreatePerson model);

    boolean delete(long id);

    List<CourseIdAndGradesView> getAllGrades(long studentId);

    List<TeacherView> getAllTeachers(long studentId);

    double getAvgGrade(long studentId);
}
