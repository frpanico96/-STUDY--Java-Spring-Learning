package com.net.pokemon.pokereview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.net.pokemon.pokereview.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(String name);

}
