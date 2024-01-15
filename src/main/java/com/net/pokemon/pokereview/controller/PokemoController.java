package com.net.pokemon.pokereview.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.net.pokemon.pokereview.dto.PokemonDto;
import com.net.pokemon.pokereview.dto.PokemonResponseDto;
import com.net.pokemon.pokereview.model.Pokemon;
import com.net.pokemon.pokereview.service.PokemonService;

@RestController
@RequestMapping("/api/v1/")
public class PokemoController {

  private PokemonService pokemonService;

  @Autowired
  public PokemoController(PokemonService pokemonService) {
    this.pokemonService = pokemonService;
  }

  @GetMapping("pokemon")
  public ResponseEntity<PokemonResponseDto> getPokemons(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

    ResponseEntity<PokemonResponseDto> result = ResponseEntity.ok(pokemonService.getAllPokemons(pageNo, pageSize));

    return result;

  }

  @GetMapping("pokemon/{pokemonId}")
  public ResponseEntity<PokemonDto> pokemon(@PathVariable int pokemonId) {
    return ResponseEntity.status(200).body(pokemonService.getPokemonById(pokemonId));
  }

  @PostMapping("pokemon/create")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {
    return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
  }

  @PutMapping("pokemon/{pokemonId}/update")
  public ResponseEntity<PokemonDto> updatePokemon(
      @RequestBody PokemonDto pokemonDto,
      @PathVariable("pokemonId") int pokemonId) {
    return new ResponseEntity<>(pokemonService.updatePokemon(pokemonDto, pokemonId), HttpStatus.OK);
  }

  @DeleteMapping("pokemon/{pokemonId}")
  public ResponseEntity<String> deletePokemon(@PathVariable("pokemonId") int pokemonId) {
    pokemonService.deletePokemon(pokemonId);
    return ResponseEntity.ok("Pokemon succesfully deleted");
  }

}
