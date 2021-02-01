package com.school.demo.models;

import com.school.demo.entity.Role;
import com.school.demo.entity.School;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateDirectorModel {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private long school_id;
}
