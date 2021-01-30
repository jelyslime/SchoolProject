package com.school.demo.dto;

import com.school.demo.entity.*;

import java.util.Set;

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
