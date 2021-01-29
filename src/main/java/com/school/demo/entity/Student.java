package com.school.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Date: 1/28/2021 Time: 2:46 PM
 * <p>
 *
 *
 * @author Vladislav_Zlatanov
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
public class Student extends Person {
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToMany(mappedBy = "students")
    Set<Course> courses;

    @ManyToMany(mappedBy = "kids")
    Set<Parent> parents;

    @OneToMany(mappedBy = "student")
    Set<Grade> grades;
}
