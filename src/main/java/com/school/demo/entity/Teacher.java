package com.school.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Date: 1/28/2021 Time: 2:49 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "teacher")
public class Teacher extends Person {
    @ManyToOne(targetEntity = School.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;
}
