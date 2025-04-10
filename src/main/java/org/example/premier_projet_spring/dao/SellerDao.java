package org.example.premier_projet_spring.dao;

import org.example.premier_projet_spring.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // @Component ou @Service
public interface SellerDao extends JpaRepository<Seller, Long> {
    Optional<Seller> findByEmail(String email);
}
