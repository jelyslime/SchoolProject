package com.school.demo.dto;


import com.school.demo.data.entity.Course;
import com.school.demo.data.entity.Student;
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
