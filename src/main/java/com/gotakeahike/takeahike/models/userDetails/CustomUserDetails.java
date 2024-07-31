package com.gotakeahike.takeahike.models.userDetails;

import com.gotakeahike.takeahike.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

public record CustomUserDetails(User user) implements UserDetails {
    public Long getUserId() {
        return this.user.getId();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getAuthorities();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}