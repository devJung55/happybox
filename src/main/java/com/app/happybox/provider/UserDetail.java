package com.app.happybox.provider;

import com.app.happybox.type.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetail implements UserDetails {

    private Long id;
    private String userId;
    private String userPassword;
    private Role userRole;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public UserDetail(Long id, String userId, String userPassword, Role userRole, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userId;
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
        return true;
    }
}
