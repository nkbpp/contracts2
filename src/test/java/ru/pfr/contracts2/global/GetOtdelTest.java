package ru.pfr.contracts2.global;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GetOtdelTest {

    @Test
    void getIt() {
        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(
                        new SimpleGrantedAuthority("ROLE_UPDATE"),
                        new SimpleGrantedAuthority("ROLE_UPDATE_IT"),
                        new SimpleGrantedAuthority("ROLE_READ_IT")
                );
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };

        String otdel = GetOtdel.get(authentication);

        assertThat(otdel).isEqualTo("IT");
    }

    @Test
    void getRSP() {
        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(
                        new SimpleGrantedAuthority("ROLE_UPDATE"),
                        new SimpleGrantedAuthority("ROLE_READ_RSP"),
                        new SimpleGrantedAuthority("ROLE_UPDATE_RSP")
                );
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };

        String otdel = GetOtdel.get(authentication);

        assertThat(otdel).isEqualTo("RSP");
    }
}