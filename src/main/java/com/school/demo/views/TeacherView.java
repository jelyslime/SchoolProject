package com.school.demo.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class TeacherView {
    private String firstName;
    private String lastName;

    private Set<CourseOnlyIdView> courses;
}
