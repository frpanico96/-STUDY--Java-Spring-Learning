package com.net.pokemon.pokereview.controller;

import java.text.StringCharacterIterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.net.pokemon.pokereview.dto.ReviewDto;
import com.net.pokemon.pokereview.service.ReviewService;

@RestController
@RequestMapping("/api/v1/")
public class ReviewController {

  private ReviewService reviewService;

  @Autowired
  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("pokemon/{pokemonId}/reviews")
  public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int pokemonId,
      @RequestBody ReviewDto reviewDto) {
    ResponseEntity<ReviewDto> result = new ResponseEntity<>(reviewService.createReview(pokemonId, reviewDto),
        HttpStatus.CREATED);

    return result;
  }

  @GetMapping("pokemon/{pokemonId}/reviews")
  public ResponseEntity<List<ReviewDto>> getReviewsByPokemonId(
      @PathVariable(value = "pokemonId") int pokemonId) {
    return ResponseEntity.ok(reviewService.getReviewByPokemonId(pokemonId));
  }

  @GetMapping("pokemon/{pokemonId}/reviews/{reviewId}")
  public ResponseEntity<ReviewDto> getReviewById(
      @PathVariable(value = "pokemonId") int pokemonId,
      @PathVariable(value = "reviewId") int reviewId) {
    return ResponseEntity.ok(reviewService.getReviewById(pokemonId, reviewId));
  }

  @PutMapping("pokemon/{pokemonId}/reviews/{reviewId}")
  public ResponseEntity<ReviewDto> updateReview(
      @PathVariable(value = "pokemonId") int pokemonId,
      @PathVariable(value = "reviewId") int reviewId,
      @RequestBody ReviewDto reviewDto) {
    return ResponseEntity.ok(reviewService.updateReview(pokemonId, reviewId, reviewDto));
  }

  @DeleteMapping("pokemon/{pokemonId}/reviews/{reviewId}")
  public ResponseEntity<String> deleteReviewById(
      @PathVariable(value = "pokemonId") int pokemonId,
      @PathVariable(value = "reviewId") int reviewId) {
    reviewService.deleteReview(pokemonId, reviewId);
    return ResponseEntity.ok("Review delete succesfully");
  }

}
