package com.school.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Date: 1/28/2021 Time: 3:04 PM
 * <p>
 *
 *
 * @author Vladislav_Zlatanov
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course")
public class Course extends BaseEntity {


    @ManyToMany
    Set<Student> students;

    @OneToMany(mappedBy = "course")
    Set<Grade> grades;

    @ManyToOne
    @JoinColumn(name = "teachers_id")
    private Teacher teacher;
}
