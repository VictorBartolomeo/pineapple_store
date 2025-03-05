package org.example.premier_projet_spring.dao;

import org.example.premier_projet_spring.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository // @Component ou @Service
public interface StateDao extends JpaRepository<State, Long> {

}
