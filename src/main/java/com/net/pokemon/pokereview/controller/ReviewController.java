package com.net.pokemon.pokereview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.net.pokemon.pokereview.dto.ReviewDto;
import com.net.pokemon.pokereview.service.ReviewService;

public class ReviewController {

  private ReviewService reviewService;

  @Autowired
  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("/pokemon/{pokemonId}/review")
  public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int pokemonId,
      @RequestBody ReviewDto reviewDto) {
    ResponseEntity<ReviewDto> result = new ResponseEntity<>(reviewService.createReview(pokemonId, reviewDto),
        HttpStatus.CREATED);

    return result;
  }

  @GetMapping("/pokemon/{pokemonId}/reviews")
  public ResponseEntity<List<ReviewDto>> getReviewsByPokemonId(
      @PathVariable(value = "pokemonId") int pokemonId) {
    return ResponseEntity.ok(reviewService.getReviewByPokemonId(pokemonId));
  }

}
