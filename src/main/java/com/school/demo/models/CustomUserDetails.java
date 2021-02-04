package com.school.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Date: 2/4/2021 Time: 5:39 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */

public class CustomUserDetails implements UserDetails {


    private final List<GrantedAuthority> authority;
    private final String username;
    private final String password;
    private final boolean active;

    public CustomUserDetails( UserModel model) {
        this.authority = Arrays.stream(model.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        this.username = model.getUsername();
        this.password = model.getPassword();
        this.active = model.isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
