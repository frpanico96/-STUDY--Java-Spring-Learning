package com.net.pokemon.pokereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.net.pokemon.pokereview.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
  // Spring data JPA will automatically creates the query
  // SELECT * FROM Review WHERE pokemonId = pokemonId
  List<Review> findByPokemonId(int pokemonId);

}
