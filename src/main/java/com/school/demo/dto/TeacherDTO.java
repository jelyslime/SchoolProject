package com.school.demo.dto;

import com.school.demo.data.entity.Course;
import com.school.demo.data.entity.Role;
import com.school.demo.data.entity.School;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO {
    private long id;
    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private School school;
    private Set<Course> courses;
}
