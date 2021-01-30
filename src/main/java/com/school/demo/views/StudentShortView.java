package com.school.demo.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentShortView {
    private String firstName;
    private String lastName;
    private SchoolShortView school;
}
