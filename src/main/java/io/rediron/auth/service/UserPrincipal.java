package io.rediron.auth.service;

import io.rediron.auth.domain.Role;
import io.rediron.auth.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal extends User implements UserDetails {

    private List<GrantedAuthority> authorities;

    public UserPrincipal(User user, List<GrantedAuthority> authorities){
        super(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.isEnabled(),
                user.isLocked(),
                user.getRoles());
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.authorities == null){
            for(Role role : getRoles()){
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
        }
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !super.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    public static UserPrincipal create(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority((role.getRoleName()))
        ).collect(Collectors.toList());

        return new UserPrincipal(user, authorities);
    }

}
