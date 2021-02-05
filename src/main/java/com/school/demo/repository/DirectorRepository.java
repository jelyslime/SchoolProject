package com.school.demo.repository;

import com.school.demo.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Date: 1/29/2021 Time: 12:10 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */
public interface DirectorRepository extends JpaRepository<Director, Long> {
    @Query(value = "SELECT * FROM director u WHERE u.username = :username",
            nativeQuery = true)
    Director findByUsernameNative(@Param("username") String username);

//    Director findByUsername(String userName);
}
