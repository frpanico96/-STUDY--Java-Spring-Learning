package com.net.pokemon.pokereview.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private JwtAuthEntryPoint jwtAuthEntryPoint;
  private CustomerUserDetailService userDetailService;

  @Autowired
  public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, CustomerUserDetailService customerUserDetailService) {
    this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    this.userDetailService = customerUserDetailService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorize) -> authorize
            // .anyRequest()
            // .authenticated()
            // .requestMatchers("/api/v1/auth/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll())
        .httpBasic(Customizer.withDefaults());
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  // @Bean
  // public UserDetailsService users() {
  // UserDetails admin = User.builder()
  // .username("admin")
  // .password("admin")
  // .roles("ADMIN")
  // .build();
  // UserDetails userOne = User.builder()
  // .username("userOne")
  // .password("password")
  // .roles("USER")
  // .build();

  // return new InMemoryUserDetailsManager(admin, userOne);
  // }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

}
