package com.net.pokemon.pokereview.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.pokemon.pokereview.dto.ReviewDto;
import com.net.pokemon.pokereview.exceptions.PokemonNotFoundException;
import com.net.pokemon.pokereview.exceptions.ReviewNotFoundException;
import com.net.pokemon.pokereview.model.Pokemon;
import com.net.pokemon.pokereview.model.Review;
import com.net.pokemon.pokereview.repository.PokemonRepository;
import com.net.pokemon.pokereview.repository.ReviewRepository;
import com.net.pokemon.pokereview.service.ReviewService;
import com.net.pokemon.pokereview.utility.ReviewUtility;

@Service
public class ReviewServiceImpl implements ReviewService {

  private PokemonRepository pokemonRepository;
  private ReviewRepository reviewRepository;

  @Autowired
  public ReviewServiceImpl(PokemonRepository pokemonRepository, ReviewRepository reviewRepository) {
    this.pokemonRepository = pokemonRepository;
    this.reviewRepository = reviewRepository;
  }

  @Override
  public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
    Review review = ReviewUtility.mapToEntity(reviewDto);

    Pokemon pokemon = pokemonRepository.findById(pokemonId)
        .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));

    System.out.println(pokemon);
    System.out.println(pokemonId);

    review.setPokemon(pokemon);

    Review savedReview = reviewRepository.save(review);

    return ReviewUtility.mapToDto(savedReview);

  }

  @Override
  public List<ReviewDto> getReviewByPokemonId(int pokemonId) {
    List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);

    return reviews.stream().map(review -> ReviewUtility.mapToDto(review)).collect(Collectors.toList());

  }

  @Override
  public ReviewDto getReviewById(int pokemonId, int reviewId) {
    Pokemon pokemon = pokemonRepository.findById(pokemonId)
        .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ReviewNotFoundException("Review not found"));

    if (review.getPokemon().getId() != pokemon.getId()) {
      throw new ReviewNotFoundException("This review does not belong to a Pokemon");
    }

    return ReviewUtility.mapToDto(review);
  }

  @Override
  public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
    Pokemon pokemon = pokemonRepository.findById(pokemonId)
        .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ReviewNotFoundException("Review not found"));

    if (review.getPokemon().getId() != pokemon.getId()) {
      throw new ReviewNotFoundException("This review does not belong to a Pokemon");
    }

    review.setTitle(reviewDto.getTitle());
    review.setContent(reviewDto.getContent());
    review.setStars(reviewDto.getStars());

    Review savedReview = reviewRepository.save(review);

    return ReviewUtility.mapToDto(savedReview);

  }

  @Override
  public void deleteReview(int pokemonId, int reviewId) {
    Pokemon pokemon = pokemonRepository.findById(pokemonId)
        .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ReviewNotFoundException("Review not found"));

    if (review.getPokemon().getId() != pokemon.getId()) {
      throw new ReviewNotFoundException("This review does not belong to a Pokemon");
    }

    reviewRepository.deleteById(reviewId);

  }

}
