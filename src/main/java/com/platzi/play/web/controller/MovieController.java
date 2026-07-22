package com.platzi.play.web.controller;


import com.platzi.play.domain.dto.MovieDto;
import com.platzi.play.domain.dto.SuggestDto;
import com.platzi.play.domain.dto.UpdateMovieDto;
import com.platzi.play.domain.service.MovieService;
import com.platzi.play.domain.service.PlatziPlayAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movies",description = "Operations for movies in platziplay")
public class MovieController {

    private final MovieService movieService;
    private final PlatziPlayAiService aiservice;
    private final String plataform;

    public MovieController(MovieService movieService, PlatziPlayAiService aiservice,@Value("${spring.application.name}") String plataform) {
        this.movieService = movieService;
        this.aiservice = aiservice;
        this.plataform = plataform;
    }
    @GetMapping("")
    public ResponseEntity<List<MovieDto>> getAll(){
        //retorna un iterable por ende se tiene que castear con el tipo de dato
        List<MovieDto> movies = this.movieService.getAll();
        if(movies.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movies);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una pelicula por su identificador",
            description = "Retorna la pelicula que coincida con el identificador enviado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "pelicula encontrada"),
                    @ApiResponse(responseCode = "404", description = "pelicula no encontrada",content = @Content)
            }
    )
    public ResponseEntity<MovieDto> getMovieById(@Parameter(description = "Identificador de la pelicula a recuperar") @PathVariable long id){
        MovieDto movieDto=this.movieService.getById(id);
        if(movieDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieDto);
    }
    @PostMapping()
    public ResponseEntity<MovieDto> addMovie(@RequestBody @Valid MovieDto movieDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.movieService.create(movieDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable long id,@RequestBody @Valid UpdateMovieDto updatemovieDto){

        return  ResponseEntity.ok(this.movieService.update(id,updatemovieDto));
    }

    @DeleteMapping("elim/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable long id){
        this.movieService.delete(id);
            return ResponseEntity.ok().build();
    }
    @PostMapping("/suggest")
    public ResponseEntity<String> generateMoviesSuggestion(@RequestBody SuggestDto suggestDto){
        return ResponseEntity.ok(this.aiservice.generateMoviesSuggestion(suggestDto.userPreferences(),plataform));
    }

}
