package org.example.premier_projet_spring.dao;

import org.example.premier_projet_spring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository // @Component ou @Service
public interface ProductDao extends JpaRepository<Product, Long> {


}
