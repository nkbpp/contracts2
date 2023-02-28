package ru.pfr.contracts2.entity.user;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {

    private final ROLE_ENUM role_enum;

    public UserRole(ROLE_ENUM role_enum) {
        this.role_enum = role_enum;
    }

/*    public UserRole(String role_enum) {
        this.role_enum = role_enum.;
    }*/

    @Override
    public String getAuthority() {
        return role_enum.getString();
    }

}
