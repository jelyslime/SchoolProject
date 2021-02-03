package com.school.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Date: 1/28/2021 Time: 2:31 PM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class Person extends BaseEntity {

    @NotNull
//    @Size(min = 5, max = 20, message = "Min 5, Max 20")
    private String username;
    //@ValidPassword
    @NotNull
//    @Size(min = 8, max = 20, message = "Min 8, Max 20")
    private String password;

    @NotNull
    private Role role;

    private String firstName;
    private String lastName;

}
