package org.example.premier_projet_spring.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.example.premier_projet_spring.view.PurchaseView;

@Getter
@Setter
@Entity
public class PurchaseLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @DecimalMin(value = "0.1")
    @JsonView(PurchaseView.class)
    protected float purchasePrice;

    @Min(1)
    @JsonView(PurchaseView.class)
    protected int quantity;


    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonView(PurchaseView.class)
    protected Product product;

    @ManyToOne
    @JoinColumn(nullable = false)
    protected Purchase purchase;


}
