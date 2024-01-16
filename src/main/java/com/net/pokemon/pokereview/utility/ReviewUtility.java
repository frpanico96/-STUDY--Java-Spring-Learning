package com.net.pokemon.pokereview.utility;

import com.net.pokemon.pokereview.dto.ReviewDto;
import com.net.pokemon.pokereview.model.Review;

public class ReviewUtility {

  public static ReviewDto mapToDto(Review review) {

    ReviewDto result = new ReviewDto();

    result.setTitle(review.getTitle());
    result.setContent(review.getContent());
    result.setStars(review.getStars());
    result.setId(review.getId());

    return result;

  }

  public static Review mapToEntity(ReviewDto reviewDto) {
    Review result = new Review();

    result.setId(reviewDto.getId());
    result.setContent(reviewDto.getContent());
    result.setTitle(reviewDto.getTitle());
    result.setStars(reviewDto.getStars());

    return result;

  }

}
