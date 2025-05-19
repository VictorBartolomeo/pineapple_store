package org.example.premier_projet_spring.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.premier_projet_spring.view.ClientView;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    public interface ValidRegister {
    }

    public interface ValidUpdate {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ClientView.class)
    protected Long id;

    @NotBlank(groups = {ValidRegister.class})
    @Email
    @Column(unique = true, nullable = false)
    @JsonView(ClientView.class)
    protected String email;

    protected String validEmailToken;

    @NotBlank
    @Column(nullable = false)
    protected String password;

}
