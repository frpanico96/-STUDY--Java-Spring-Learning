package com.net.pokemon.pokereview.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.net.pokemon.pokereview.dto.AuthResponseDto;
import com.net.pokemon.pokereview.dto.LoginDto;
import com.net.pokemon.pokereview.dto.RegisterDto;
import com.net.pokemon.pokereview.model.Role;
import com.net.pokemon.pokereview.model.UserEntity;
import com.net.pokemon.pokereview.repository.RoleRepository;
import com.net.pokemon.pokereview.repository.UserRepository;
import com.net.pokemon.pokereview.security.JwtGenerator;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

  private AuthenticationManager authenticationManager;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;
  private JwtGenerator jwtGenerator;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager,
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder,
      JwtGenerator jwtGenerator) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtGenerator = jwtGenerator;
  }

  @PostMapping("register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    if (userRepository.existsByUsername(registerDto.getUsername())) {
      return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
    }

    UserEntity user = new UserEntity();
    user.setUsername(registerDto.getUsername());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

    Role roles = roleRepository.findByName("USER").get();
    user.setRoles(Collections.singletonList(roles));

    userRepository.save(user);

    return ResponseEntity.ok("User registerd correctly");

  }

  @PostMapping("login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtGenerator.generatedToken(authentication);
    return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);

  }

}
