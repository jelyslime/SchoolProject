package com.school.demo.dto;

import com.school.demo.data.entity.Role;
import com.school.demo.data.entity.School;
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
    private Role role;
    private String firstName;
    private String lastName;
    private School school;
}
