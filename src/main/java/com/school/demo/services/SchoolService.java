package com.school.demo.services;

import com.school.demo.dto.SchoolDTO;

import java.util.Map;

public interface SchoolService {

    SchoolDTO get(long schoolId);

//    List<TeacherDTO> getAllTeachersInSchool(long schoolId);
//
//    List<ParentDTO> getAllParentsInSchool(long schoolId);
//
//    List<StudentDTO> getAllStudentsInSchool(long schoolId);

    Map<String, Double> avgGradeOnStudents(long schoolId);

    Map<String, Double> avgGradeOnStudentsWhoHaveMoreThenFourPointFive(long schoolId);

    Map<String, Double> avgGradeOnStudentsWhoHaveLessThenFourPointFive(long schoolId);
}
