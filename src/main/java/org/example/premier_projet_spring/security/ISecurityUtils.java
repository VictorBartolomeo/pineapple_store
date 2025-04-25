package org.example.premier_projet_spring.security;

public interface ISecurityUtils {
    String getRole(AppUserDetails userDetails);

    String generateToken(AppUserDetails userDetails);

    String getSubjectFromJwt(String jwt);
}
