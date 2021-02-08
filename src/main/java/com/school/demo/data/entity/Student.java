package com.school.demo.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @ManyToOne(targetEntity = School.class, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"director", "teachers", "students"})
    private School school;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "students")
    private Set<Course> courses;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "kids")
    private Set<Parent> parents;


    @OneToMany(mappedBy = "student")
    private Set<Grade> grades;


}
