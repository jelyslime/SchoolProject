package com.school.demo.services;


import com.school.demo.dto.TeacherDTO;
import com.school.demo.entity.Grade;
import com.school.demo.entity.Teacher;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;

import java.util.List;
import java.util.Map;

public interface TeacherService {

    //TODO CREATE CRUD
    TeacherDTO get(long teacherId);

    Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getAllStudentGrades(long teacherId);

//    boolean assignSchool(long teacherId,long schoolId);


    public boolean removeSchool(long id);


    Grade addGrade(long id, long course_id, double grade, long student_id);

    Grade updateGrade(long id, long course_id, long grade_id, double grade);

    void deleteGrade(long id, long course_id, long grade_id);
}
