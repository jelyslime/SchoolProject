package com.school.demo.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StudentView {
    private String firstName;
    private String lastName;
    private SchoolView school;
    private Set<GradeView> grades;
    private Set<CourseOnlyIdView> courses;

}
