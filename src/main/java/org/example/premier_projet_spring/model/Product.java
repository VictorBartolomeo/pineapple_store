package org.example.premier_projet_spring.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.premier_projet_spring.view.PurchaseView;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    @JsonView(PurchaseView.class)
    protected String name;

    @Column(length = 10, nullable = false, unique = true)
    @Length(min = 10, max = 10)
    @NotBlank
    protected String code;

    @Column(columnDefinition = "TEXT")
    @JsonView(PurchaseView.class)
    protected String description;

    @DecimalMin(value = "0.1")
    protected float price;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonView(PurchaseView.class)
    protected State state;

    @ManyToMany
    @JoinTable(
            name = "product_label",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    @JsonView(PurchaseView.class)
    protected List<Label> labels = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    Seller creator;


}
