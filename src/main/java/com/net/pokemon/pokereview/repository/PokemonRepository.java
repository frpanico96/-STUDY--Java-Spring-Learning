package com.net.pokemon.pokereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.net.pokemon.pokereview.model.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
}