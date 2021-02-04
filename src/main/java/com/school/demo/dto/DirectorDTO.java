package com.school.demo.dto;

import com.school.demo.entity.Role;
import com.school.demo.entity.School;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class DirectorDTO {
    private long id;
    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private School school;
}
