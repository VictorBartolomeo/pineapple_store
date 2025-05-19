package org.example.premier_projet_spring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailValidationDto {

    protected String token;
    protected String email;
    protected boolean CGU;
//    protected String password;
}
