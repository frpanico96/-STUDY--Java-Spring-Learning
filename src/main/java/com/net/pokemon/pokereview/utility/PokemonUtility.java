package com.net.pokemon.pokereview.utility;

import com.net.pokemon.pokereview.dto.PokemonDto;
import com.net.pokemon.pokereview.model.Pokemon;

public class PokemonUtility {

  public static PokemonDto mapToDto(Pokemon pokemon) {
    PokemonDto pokemonDto = new PokemonDto();
    pokemonDto.setId(pokemon.getId());
    pokemonDto.setName(pokemon.getName());
    pokemonDto.setType(pokemon.getType());

    return pokemonDto;
  }

  public static Pokemon mapToEntity(PokemonDto pokemonDto) {
    Pokemon pokemon = new Pokemon();
    pokemon.setId(pokemonDto.getId());
    pokemon.setName(pokemonDto.getName());
    pokemon.setType(pokemonDto.getType());

    return pokemon;
  }

}
