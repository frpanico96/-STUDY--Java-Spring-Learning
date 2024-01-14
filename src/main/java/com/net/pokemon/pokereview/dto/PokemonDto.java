package com.net.pokemon.pokereview.dto;

import com.net.pokemon.pokereview.model.Pokemon;

import lombok.Data;

@Data
public class PokemonDto {
  private int id;
  private String name;
  private String type;

}
