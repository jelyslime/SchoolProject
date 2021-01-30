package com.school.demo.dto;

import com.school.demo.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {
    private long id;
    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private School school;
    private Set<Course> courses;
    private Set<Parent> parents;
    private Set<Grade> grades;
}
