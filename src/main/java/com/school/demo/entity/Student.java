package com.school.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Date: 1/28/2021 Time: 2:46 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
public class Student extends Person {
    @ManyToOne(targetEntity=School.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"director", "teachers", "students"})
    private School school;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "students")
    @JsonIgnoreProperties("student")
    private Set<Course> courses;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "kids")
    private Set<Parent> parents;


    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties("student")
    private Set<Grade> grades;



}
