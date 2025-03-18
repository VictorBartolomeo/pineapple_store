package org.example.premier_projet_spring.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.premier_projet_spring.view.PurchaseView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    // @NotNull vérification côté controller
    @NotNull
    @JsonView(PurchaseView.class)
    protected LocalDateTime date;

    @OneToMany(mappedBy = "purchase")
    @JsonView(PurchaseView.class)
    protected List<PurchaseLine> purchases = new ArrayList<>();


}
