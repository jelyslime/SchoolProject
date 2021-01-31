package com.school.demo.repository;

import com.school.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:13 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
