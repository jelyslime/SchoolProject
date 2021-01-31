package com.school.demo.services;

import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.ParentDirectorView;
import com.school.demo.views.TeacherView;

import java.util.List;

public interface DirectorService {
    List<CourseIdAndGradesView> getAllCoursesAndAllGrades(long directorId);

    List<TeacherView> getAllTeachers(long directorId);

    //•	As a director I would like to see all parents.
    List<ParentDirectorView> getAllParents(long directorId);


}
