package com.net.pokemon.pokereview.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.pokemon.pokereview.dto.PokemonDto;
import com.net.pokemon.pokereview.model.Pokemon;
import com.net.pokemon.pokereview.repository.PokemonRepository;
import com.net.pokemon.pokereview.service.PokemonService;
import com.net.pokemon.pokereview.utility.PokemonUtility;

@Service
public class PokemonServiceImpl implements PokemonService {

  private PokemonRepository pokemonRepository;

  @Autowired
  public PokemonServiceImpl(PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Override
  public PokemonDto createPokemon(PokemonDto pokemonDto) {
    Pokemon pokemon = new Pokemon();
    pokemon.setName(pokemonDto.getName());
    pokemon.setType(pokemonDto.getType());

    Pokemon savedPokemon = pokemonRepository.save(pokemon);

    PokemonDto result = PokemonUtility.mapToDto(savedPokemon);

    return result;

  }

  @Override
  public List<PokemonDto> getAllPokemons() {
    List<Pokemon> allPokemons = pokemonRepository.findAll();

    return allPokemons.stream()
        .map(pokemon -> PokemonUtility.mapToDto(pokemon))
        .collect(Collectors.toList());
  }
}
