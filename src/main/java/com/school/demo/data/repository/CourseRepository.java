package com.school.demo.data.repository;

import com.school.demo.data.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:07 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
}
