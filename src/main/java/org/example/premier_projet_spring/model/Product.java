package org.example.premier_projet_spring.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column(length = 10, nullable = false, unique = true)
    protected String code;

    @Column(columnDefinition = "TEXT")
    protected String description;
    protected float price;
    
    @ManyToOne
    protected State state;


}
