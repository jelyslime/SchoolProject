package com.school.demo.data.repository;

import com.school.demo.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:12 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
