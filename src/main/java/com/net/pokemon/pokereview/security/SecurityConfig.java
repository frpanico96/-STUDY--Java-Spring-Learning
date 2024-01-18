package com.net.pokemon.pokereview.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(Customizer.withDefaults())
        .authorizeHttpRequests((authorize) -> authorize
            // .anyRequest()
            // .authenticated()
            .requestMatchers(HttpMethod.GET).permitAll())
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public UserDetailsService users() {
    UserDetails admin = User.builder()
        .username("admin")
        .password("admin")
        .roles("ADMIN")
        .build();
    UserDetails userOne = User.builder()
        .username("userOne")
        .password("password")
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(admin, userOne);
  }
}