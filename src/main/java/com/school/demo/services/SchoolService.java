package com.school.demo.services;

import com.school.demo.dto.SchoolDTO;
import com.school.demo.models.CreateSchoolModel;

import java.util.Map;

public interface SchoolService {

    SchoolDTO get(long schoolId);

    SchoolDTO create(CreateSchoolModel model);

    boolean edit(long id, CreateSchoolModel model);

    boolean delete(long id);

    boolean assignDirector(long schoolId, long directorID);

    boolean removeDirector(long schoolId);

    boolean addStudent(long schoolId, long studentId);

    boolean removeStudent(long schoolId, long studentId);

    boolean assignTeacher(long schoolId, long teacherId);

    boolean removeTeacher(long schoolId, long teacherId);

    boolean assignStudentToCourse(long schoolID, long courseID, long studentId);

    boolean removeStudentFromCourse(long schoolID, long courseID, long studentId);


    Map<String, Double> avgGradeOnStudents(long schoolId);

    Map<String, Double> avgGradeOnStudentsWhoHaveMoreThenFourPointFive(long schoolId);

    Map<String, Double> avgGradeOnStudentsWhoHaveLessThenFourPointFive(long schoolId);
}
