package com.school.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Date: 1/28/2021 Time: 3:17 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "parents")
public class Parent extends Person {
    @ManyToMany
    @JoinTable(
            name = "parents_kids",
            joinColumns = @JoinColumn(name = "parents_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> kids;
}
