package com.school.demo.repository;

import com.school.demo.entity.Student;
import org.springframework.data.repository.CrudRepository;

/**
 * Date: 1/29/2021 Time: 12:13 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */
public interface StudentRepository extends CrudRepository<Student,Long> {
}
