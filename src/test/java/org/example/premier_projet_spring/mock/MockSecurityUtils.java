package org.example.premier_projet_spring.mock;

import org.example.premier_projet_spring.security.AppUserDetails;
import org.example.premier_projet_spring.security.ISecurityUtils;

public class MockSecurityUtils implements ISecurityUtils {

    private String role;

    public MockSecurityUtils(String role) {
        this.role = role;
    }

    @Override
    public String getRole(AppUserDetails userDetails) {
        return role;
    }

    @Override
    public String generateToken(AppUserDetails userDetails) {
        return "";
    }

    @Override
    public String getSubjectFromJwt(String jwt) {
        return "";
    }
}
