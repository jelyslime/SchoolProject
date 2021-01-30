package com.school.demo.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
public class PersonNamesView {
    private String firstName;
    private String lastName;
}
