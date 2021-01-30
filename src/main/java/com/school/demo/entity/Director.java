package com.school.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Date: 1/28/2021 Time: 2:54 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "director")
public class Director extends Person {

    @OneToOne(mappedBy = "director")
    private School school;
}
