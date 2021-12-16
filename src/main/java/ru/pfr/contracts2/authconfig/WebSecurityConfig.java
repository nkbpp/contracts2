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
                .antMatchers("/contract/admin/**").hasAnyRole(ROLE_ENUM.ROLE_ADMIN.getStringNoRole())
                .antMatchers("/contract/main").hasAnyRole(ROLE_ENUM.ROLE_UPDATE.getStringNoRole())
                .antMatchers("/contract/main/**").hasAnyRole(ROLE_ENUM.ROLE_UPDATE.getStringNoRole())
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
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**"); //папка которую игнорировать
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }
}
