package com.net.pokemon.pokereview.service;

import java.util.List;

import com.net.pokemon.pokereview.dto.ReviewDto;

public interface ReviewService {
  ReviewDto createReview(int pokemonId, ReviewDto reviewDto);

  List<ReviewDto> getReviewByPokemonId(int pokemonId);
}
