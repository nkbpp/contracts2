package ru.pfr.contracts2.config.sec;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import ru.pfr.contracts2.entity.user.ROLE_ENUM;
import ru.pfr.contracts2.entity.user.Rayon;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.entity.user.UserRole;

import java.util.Arrays;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        var rayon = new Rayon();
        rayon.setId(1L);
        User principalUser =
                new User(
                        customUser.login(),
                        rayon
                );
        principalUser.setUserRoles(
                Arrays.stream(customUser.roles())
                        .map(s -> new UserRole(ROLE_ENUM.valueOf(s)))
                        .toList()
        );

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        principalUser,
                        "password",
                        principalUser.getAuthorities()
                );
        context.setAuthentication(auth);

        return context;
    }
}
