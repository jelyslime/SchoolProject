package com.school.demo.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchoolView {
    DirectorView director;
    private String name;
    private String address;
}
