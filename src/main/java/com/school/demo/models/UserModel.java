package com.school.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Date: 2/4/2021 Time: 5:40 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */
@AllArgsConstructor
@Getter
@Setter
public class UserModel {
    private final String username;
    private final String password;
    private final boolean active;
    private final String role;

}
