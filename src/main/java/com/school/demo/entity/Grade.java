package com.school.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Date: 1/28/2021 Time: 3:08 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "grade")
public class Grade extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    //    @NotBlank
//    @DecimalMax("6.0")
//    @DecimalMin("2.0")
    private double grade;
}
