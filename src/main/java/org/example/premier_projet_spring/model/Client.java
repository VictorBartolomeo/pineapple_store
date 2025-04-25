package org.example.premier_projet_spring.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.example.premier_projet_spring.view.ClientView;

@Getter
@Setter
@Entity
public class Client extends User {

    @JsonView(ClientView.class)
    protected String clientNumber;

}
