package com.school.demo.dto;

import com.school.demo.entity.Grade;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {
    private long id;
    private Set<Student> students;
    private Set<Grade> grades;
    private Teacher teacher;
}
