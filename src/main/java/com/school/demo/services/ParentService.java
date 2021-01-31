package com.school.demo.services;

import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;

import java.util.List;
import java.util.Map;

public interface ParentService {
    Map<String, List<CourseIdAndGradesView>> getAllGrades(long parentId);

    Map<String, List<TeacherView>> getAllTeachers(long parentId);
}
