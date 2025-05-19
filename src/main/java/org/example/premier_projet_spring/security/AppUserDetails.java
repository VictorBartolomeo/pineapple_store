package org.example.premier_projet_spring.security;

import lombok.Getter;
import org.example.premier_projet_spring.model.Client;
import org.example.premier_projet_spring.model.Seller;
import org.example.premier_projet_spring.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class AppUserDetails implements UserDetails {

    protected User user;

    public AppUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        boolean isClient = user instanceof Client;

        if (isClient) {

            return List.of(new SimpleGrantedAuthority(isClient ? "ROLE_CLIENT" : "ROLE_SELLER"));

        }
        else {
            Seller seller = (Seller) user;
            return List.of(new SimpleGrantedAuthority("ROLE_" + (seller.isChief() ? "CHIEF" : "SELLER")));
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.getValidEmailToken() == null;
    }
}
