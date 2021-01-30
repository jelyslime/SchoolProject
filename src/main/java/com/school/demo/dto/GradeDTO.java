package com.school.demo.dto;


import com.school.demo.entity.Course;
import com.school.demo.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class GradeDTO {
    Student student;
    Course course;
    double grade;
    private long id;
}
