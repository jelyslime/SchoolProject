package com.school.demo.repository;

import com.school.demo.entity.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Date: 1/29/2021 Time: 12:12 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */
public interface PersonRepository extends CrudRepository<Person,Long> {
}
