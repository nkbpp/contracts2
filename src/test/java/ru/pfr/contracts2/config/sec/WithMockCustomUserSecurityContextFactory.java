package ru.pfr.contracts2.config.sec;

public class WithMockCustomUserSecurityContextFactory
        //implements WithSecurityContextFactory<WithMockCustomUser>
{

/*    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User principal =
                new UserInfo(
                        customUser.login(),
                        Arrays.stream(customUser.roles())
                                .map(UserRole::new)
                                .toList(),
                        "",
                        ""
                );

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        "password",
                        principal.getAuthorities()
                );
        context.setAuthentication(auth);

        return context;
    }*/
}
