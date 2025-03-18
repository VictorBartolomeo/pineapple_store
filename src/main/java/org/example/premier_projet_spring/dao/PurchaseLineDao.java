package org.example.premier_projet_spring.dao;

import org.example.premier_projet_spring.model.PurchaseLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository // @Component ou @Service
public interface PurchaseLineDao extends JpaRepository<PurchaseLine, Long> {


}
