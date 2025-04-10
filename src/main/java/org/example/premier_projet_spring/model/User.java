package org.example.premier_projet_spring.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.premier_projet_spring.security.Role;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    protected String email;

    @NotBlank
    @Column(nullable = false)
    protected String password;

    @Enumerated
    @Column(columnDefinition = "ENUM('USER', 'EDITOR', 'ADMINISTRATOR')")
    protected Role role;


}
