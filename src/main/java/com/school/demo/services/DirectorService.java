package com.school.demo.services;

import com.school.demo.dto.CourseGradesDTO;
import com.school.demo.dto.ParentDTO;
import com.school.demo.dto.TeacherDTO;

import java.util.List;

public interface DirectorService {
    CourseGradesDTO getAllCoursesAndAllGrades();

    List<TeacherDTO> getAllTeachers(long schoolId);

    List<ParentDTO> getAllParents(long schoolId);


}
