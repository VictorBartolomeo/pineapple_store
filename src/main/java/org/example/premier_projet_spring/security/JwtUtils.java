package org.example.premier_projet_spring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtUtils {

    public String generateToken(AppUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
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
