package ru.pfr.contracts2.authconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.pfr.contracts2.entity.user.ROLE_ENUM;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthProvider authProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//включить авторизацию
                //.antMatchers("**/*.html").permitAll()
                .antMatchers("/contract/admin/**").hasAnyRole(
                        ROLE_ENUM.ROLE_ADMIN.getStringNoRole())
                .antMatchers("/contract/main").hasAnyRole(
                        ROLE_ENUM.ROLE_UPDATE.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_IT.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_IT.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_AXO.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_AXO.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_RSP.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_RSP.getStringNoRole())
                .antMatchers("/contract/main/**").hasAnyRole(
                        ROLE_ENUM.ROLE_UPDATE.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_IT.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_IT.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_AXO.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_AXO.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_RSP.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_RSP.getStringNoRole())
                .antMatchers("/contract/ob/**").hasAnyRole(
                        ROLE_ENUM.ROLE_UPDATE.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_RSP.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_RSP.getStringNoRole())
                .antMatchers("/contract/it/**").hasAnyRole(
                        ROLE_ENUM.ROLE_UPDATE_IT.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_IT.getStringNoRole())
                .antMatchers("/contract/dop/**").hasAnyRole(
                        ROLE_ENUM.ROLE_UPDATE_IT.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_IT.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_AXO.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_AXO.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_RSP.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_RSP.getStringNoRole())
                .antMatchers("/contract/rsp/**").hasAnyRole(
                        ROLE_ENUM.ROLE_UPDATE_RSP.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_RSP.getStringNoRole())
                .antMatchers("/contract/axo/**").hasAnyRole(
                        ROLE_ENUM.ROLE_READ_AXO.getStringNoRole(),
                        ROLE_ENUM.ROLE_UPDATE_AXO.getStringNoRole())
                .antMatchers("**/*.css").permitAll()
                .antMatchers("**/*.js").permitAll()
                //.antMatchers("/", "/pkv").permitAll()//полный доступ
                .anyRequest().authenticated()//для стальных авторизация
                .and()
                .formLogin()
                .loginPage("/contract/login").permitAll()//маппинг логина разрешаем пользоваться всем
                //.failureHandler(customAuthFailureHandler)
                .and()
                .logout()//включаем логаут
                .permitAll();//разрешаем пользоваться всем
        /*
        // отключает csrf
        http.csrf().disable();
        http.headers().disable();*/
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**"); //папка которую игнорировать
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

/*    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("testUser")
                .password("secret")
                .roles(
                        ROLE_ENUM.ROLE_UPDATE_IT.getStringNoRole(),
                        ROLE_ENUM.ROLE_READ_IT.getStringNoRole()
                )
                .build();
        return new InMemoryUserDetailsManager(user);
    }*/
}
