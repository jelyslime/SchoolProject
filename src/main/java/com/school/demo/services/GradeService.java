package com.school.demo.services;

import com.school.demo.dto.GradeDTO;

import java.util.List;

public interface GradeService {
    List<GradeDTO> getAllGrades();

    List<GradeDTO> getAllGradesByCourseId();

    List<GradeDTO> getAllGradesByCourseIdAndStudentId();
}
