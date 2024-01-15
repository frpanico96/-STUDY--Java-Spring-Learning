package com.net.pokemon.pokereview.service;

import java.util.List;

import com.net.pokemon.pokereview.dto.PokemonDto;
import com.net.pokemon.pokereview.dto.PokemonResponseDto;

public interface PokemonService {

  PokemonDto createPokemon(PokemonDto pokemonDto);

  PokemonResponseDto getAllPokemons(int pageNo, int pageSize);

  PokemonDto getPokemonById(int id);

  PokemonDto updatePokemon(PokemonDto pokemonDto, int id);

  void deletePokemon(int id);
}
