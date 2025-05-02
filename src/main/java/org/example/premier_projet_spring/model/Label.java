package org.example.premier_projet_spring.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.premier_projet_spring.view.ProductViewClient;

@Getter
@Setter
@Entity
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    @JsonView(ProductViewClient.class)
    protected String name;

    @Column(nullable = false)
    @JsonView(ProductViewClient.class)
    protected String color;

}
