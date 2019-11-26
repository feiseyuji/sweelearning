package com.womenhz.swee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("sign/**").hasRole("USER")
                .antMatchers("user/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin1").password(new BCryptPasswordEncoder().encode("admin1"))
                .roles("ADMIN","USER")
                .and()
                .withUser("user1").password(new BCryptPasswordEncoder().encode("user1")).roles("USER");
    }
}
