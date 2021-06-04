package com.moneystats.MoneyStats.security;

import com.moneystats.MoneyStats.auth.Authservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig<AuthService> extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final Authservice authService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, Authservice authService) {
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .authorizeRequests().antMatchers("/", "/css/**", "/img/**", "/logos/**",
                "/loginpage.html", "/jquery-3.6.0.min.js", "/js/**", "/signup/add", "/login").permitAll()
                //acesso ai template Admin
                .antMatchers("/homepage.html").hasAnyRole(Roles.ADMIN, Roles.USER)
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/forbidden.html")
                .and()
                .formLogin()
                .loginPage("/loginpage.html")
                .loginProcessingUrl("/login")
                .permitAll()
                // PAGINA RENDER AFTER LOGIN
                .defaultSuccessUrl("/homepage.html", true)
                .failureUrl("/loginpage.html")
                .passwordParameter("password")
                .usernameParameter("username")
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/logout.html")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/loginpage.html");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(authService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

}