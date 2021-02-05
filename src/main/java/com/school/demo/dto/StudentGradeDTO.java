package com.school.demo.dto;

import com.school.demo.data.entity.Grade;
import com.school.demo.data.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class StudentGradeDTO {
    Student student;
    List<Grade> grades;
}
