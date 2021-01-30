package com.school.demo.services;


import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.StudentGradeDTO;
import com.school.demo.entity.Student;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface TeacherService {
    ;
    Map<Long, StudentGradeDTO> getAllGrades();
}
