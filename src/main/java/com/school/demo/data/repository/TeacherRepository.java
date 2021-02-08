package com.school.demo.data.repository;

import com.school.demo.data.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:14 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
