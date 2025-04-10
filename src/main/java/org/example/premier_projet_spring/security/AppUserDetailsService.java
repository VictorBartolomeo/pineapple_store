package org.example.premier_projet_spring.security;

import org.example.premier_projet_spring.dao.UserDao;
import org.example.premier_projet_spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    protected UserDao userDao;

    @Autowired
    public AppUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDao.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AppUserDetails(optionalUser.get());
    }
}
