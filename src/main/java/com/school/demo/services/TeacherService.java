package com.school.demo.services;


import com.school.demo.dto.StudentGradeDTO;

import java.util.Map;

public interface TeacherService {
    ;

    Map<Long, StudentGradeDTO> getAllGrades();
}
