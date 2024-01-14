package com.net.pokemon.pokereview.service;

import java.util.List;

import com.net.pokemon.pokereview.dto.PokemonDto;

public interface PokemonService {

  PokemonDto createPokemon(PokemonDto pokemonDto);

  List<PokemonDto> getAllPokemons();
}
