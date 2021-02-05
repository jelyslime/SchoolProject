package com.school.demo.dto;

import com.school.demo.data.entity.Director;
import com.school.demo.data.entity.Student;
import com.school.demo.data.entity.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SchoolDTO {
    private long id;
    private String name;
    private String address;
    private Director director;
    private List<Teacher> teachers;
    private List<Student> students;
}
