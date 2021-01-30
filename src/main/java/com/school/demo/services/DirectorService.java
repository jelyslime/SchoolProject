package com.school.demo.services;

import com.school.demo.dto.CourseGradesDTO;
import com.school.demo.dto.ParentDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.views.CourseIdAndGradesView;

import java.util.List;

public interface DirectorService {
    List<CourseIdAndGradesView> getAllCoursesAndAllGrades(long directorId);

    List<TeacherDTO> getAllTeachers(long schoolId);

    List<ParentDTO> getAllParents(long schoolId);


}
