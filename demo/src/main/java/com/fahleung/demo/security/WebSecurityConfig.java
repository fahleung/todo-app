package com.fahleung.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.fahleung.demo.security.ApplicationUserRole.*;

import com.fahleung.demo.user.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private static final String login = "login";

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // re enable later csrf
                .antMatchers("/" + login, "/users/register", "/dist/*", "/images/*", "/app/js/*").permitAll()
                .antMatchers("/users/**").hasRole(USER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/" + login).permitAll()
                .defaultSuccessUrl("/index", true).failureUrl("/login?error=true")
                .and()
                .rememberMe()
                .and()
                .logout().logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .clearAuthentication(true).invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me").logoutSuccessUrl("/" + login);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}
