package com.school.demo.data.repository;

import com.school.demo.data.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:11 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
public interface ParentRepository extends JpaRepository<Parent, Long> {
}
