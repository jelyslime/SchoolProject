package com.school.demo.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ParentDirectorView {
    private String firstName;
    private String lastName;

    private Set<PersonNamesView> kids;
}
