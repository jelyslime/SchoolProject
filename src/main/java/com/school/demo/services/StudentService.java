package com.school.demo.services;

import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface StudentService {
    List<CourseIdAndGradesView> getAllGrades(long studentId);
    List<TeacherView> getAllTeachers(long studentId);

    double getAvgGrade(long studentId);
}
