package edu.mngprj.mgprj.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModded implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<UserRole> roles = user.getRoles();
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UserRole role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole_name()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getUserLogin().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserLogin().getUsername();
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
        return user.is_enable();
    }
}
