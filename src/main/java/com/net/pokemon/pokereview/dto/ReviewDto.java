package com.net.pokemon.pokereview.dto;

import com.net.pokemon.pokereview.model.Pokemon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
  private int id;
  private String title;
  private String content;
  private int stars;
}
