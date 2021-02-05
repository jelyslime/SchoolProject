package com.school.demo.data.repository;

import com.school.demo.data.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 1/29/2021 Time: 12:10 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */
public interface DirectorRepository extends JpaRepository<Director, Long> {
}
