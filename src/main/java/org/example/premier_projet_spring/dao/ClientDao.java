package org.example.premier_projet_spring.dao;

import org.example.premier_projet_spring.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // @Component ou @Service
public interface ClientDao extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
}
