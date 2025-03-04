package org.example.premier_projet_spring.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {

    @Id
    protected Integer id;

    @Column(nullable = false)
    protected String name;

    @Column(length = 10, nullable = false, unique = true)
    protected String code;

    @Column(columnDefinition = "TEXT")
    protected String description;
    protected float price;


}
