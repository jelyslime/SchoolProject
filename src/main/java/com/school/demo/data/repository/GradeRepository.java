package com.school.demo.data.repository;

import com.school.demo.data.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:10 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
