package com.school.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CourseGradesDTO {
    List<CourseDTO> courses;
    List<GradeDTO> grades;
}
