package com.net.pokemon.pokereview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.net.pokemon.pokereview.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  Optional<UserEntity> findByUsername(String username);

  Boolean existsByUsername(String username);
}
