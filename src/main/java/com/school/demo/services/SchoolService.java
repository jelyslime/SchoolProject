package com.school.demo.services;

import com.school.demo.dto.ParentDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.dto.TeacherDTO;

import java.util.List;

public interface SchoolService {

    List<TeacherDTO> getAllTeachersInSchool(long schoolId);

    List<ParentDTO> getAllParentsInSchool(long schoolId);

    List<StudentDTO> getAllStudentsInSchool(long schoolId);
}
