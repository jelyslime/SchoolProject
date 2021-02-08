package com.school.demo.data.repository;

import com.school.demo.data.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:13 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
