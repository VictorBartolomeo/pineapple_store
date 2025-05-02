package org.example.premier_projet_spring.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.example.premier_projet_spring.view.ProductViewSeller;

@Getter
@Setter
@Entity

public class Seller extends User {

    @JsonView({ProductViewSeller.class})
    protected int salary;

    @JsonView({ProductViewSeller.class})
    boolean chief = false;

}
