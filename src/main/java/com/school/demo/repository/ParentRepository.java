package com.school.demo.repository;

import com.school.demo.entity.Director;
import com.school.demo.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:11 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */
public interface ParentRepository extends JpaRepository<Parent, Long> {
    Parent findByUsername(String userName);
}
