package org.example.premier_projet_spring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SecurityUtils {

    public String getRole(AppUserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).findFirst().orElse(null);
    }

    public String generateToken(AppUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(Map.of("role", getRole(userDetails)))
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }

    public String getSubjectFromJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
