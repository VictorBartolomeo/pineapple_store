package org.example.premier_projet_spring.model;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Seller extends User {

    protected int salary;

    boolean chief = false;

}
