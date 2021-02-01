package com.school.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateSchoolModel {
    private long id;
    private String name;
    private String address;
}
