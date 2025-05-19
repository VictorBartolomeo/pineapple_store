package org.example.premier_projet_spring.security;

import org.example.premier_projet_spring.dao.ClientDao;
import org.example.premier_projet_spring.dao.SellerDao;
import org.example.premier_projet_spring.model.Client;
import org.example.premier_projet_spring.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    protected SellerDao sellerDao;
    protected ClientDao clientDao;

    @Autowired
    public AppUserDetailsService(ClientDao clientDao, SellerDao sellerDao) {
        this.clientDao = clientDao;
        this.sellerDao = sellerDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Client> optionalClient = clientDao.findByEmail(email);

        if (optionalClient.isEmpty()) {
            Optional<Seller> optionalSeller = sellerDao.findByEmail(email);

            if (optionalSeller.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            else {
                return new AppUserDetails(optionalSeller.get());
            }
        }
        else {
            return new AppUserDetails(optionalClient.get());
        }
    }
}
