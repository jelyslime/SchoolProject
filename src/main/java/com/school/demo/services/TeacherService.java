package com.school.demo.services;


import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface TeacherService {


    Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getAllStudentGrades(long teacherId);

    void addGrade(long id, long course_id, double grade,  long student_id);
}
