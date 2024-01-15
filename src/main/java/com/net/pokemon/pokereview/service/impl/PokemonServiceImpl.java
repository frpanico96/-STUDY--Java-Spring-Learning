package com.net.pokemon.pokereview.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.net.pokemon.pokereview.dto.PokemonDto;
import com.net.pokemon.pokereview.dto.PokemonResponseDto;
import com.net.pokemon.pokereview.exceptions.PokemonNotFoundException;
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
  public PokemonResponseDto getAllPokemons(int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);

    Page<Pokemon> allPokemons = pokemonRepository.findAll(pageable);
    List<Pokemon> listOfPokemons = allPokemons.getContent();
    List<PokemonDto> content = listOfPokemons.stream()
        .map(pokemon -> PokemonUtility.mapToDto(pokemon))
        .collect(Collectors.toList());

    PokemonResponseDto pokemonResponseDto = new PokemonResponseDto();
    pokemonResponseDto.setContent(content);
    pokemonResponseDto.setPageNo(allPokemons.getNumber());
    pokemonResponseDto.setPageSize(allPokemons.getSize());
    pokemonResponseDto.setTotalElements(allPokemons.getTotalElements());
    pokemonResponseDto.setTotalPages(allPokemons.getTotalPages());
    pokemonResponseDto.setLast(allPokemons.isLast());

    return pokemonResponseDto;
  }

  @Override
  public PokemonDto getPokemonById(int id) {
    Pokemon pokemon = pokemonRepository.findById(id)
        .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
    return PokemonUtility.mapToDto(pokemon);
  }

  @Override
  public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
    pokemonRepository.findById(id)
        .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
    Pokemon pokemonToSave = PokemonUtility.mapToEntity(pokemonDto);
    pokemonToSave.setId(id);
    Pokemon savedPokemon = pokemonRepository.save(pokemonToSave);

    return PokemonUtility.mapToDto(savedPokemon);
  }

  @Override
  public void deletePokemon(int id) {
    pokemonRepository.findById(id)
        .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
    pokemonRepository.deleteById(id);
    return;
  }
}
