package org.example.premier_projet_spring.model;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Client extends User {

    protected String clientNumber;

}
