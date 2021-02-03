package com.school.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreatePersonModel {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
