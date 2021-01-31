package com.school.demo.services;


import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Map;

public interface TeacherService {


    Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getAllStudentGrades(long teacherId);

    void addGrade(long id, long course_id, double grade, long student_id) throws InvalidObjectException;

    void updateGrade(long id, long course_id, long grade_id, double grade) throws InvalidObjectException;

    void deleteGrade(long id, long course_id, long grade_id) throws InvalidObjectException;
}
