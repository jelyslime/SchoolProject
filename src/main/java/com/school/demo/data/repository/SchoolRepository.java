package com.school.demo.data.repository;

import com.school.demo.data.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:12 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
public interface SchoolRepository extends JpaRepository<School, Long> {
}
