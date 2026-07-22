package com.platzi.play.domain.service;

import com.platzi.play.domain.dto.MovieDto;
import com.platzi.play.domain.dto.UpdateMovieDto;
import com.platzi.play.domain.repository.MovieRepository;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    @Tool("BUSCAR TODAS LAS PELICULAS DE LA BASE DE DATOS")
    public List<MovieDto> getAll() {
       return movieRepository.getAll();
    }

    public MovieDto getById(Long id) {
        return movieRepository.getById(id);
    }

    public MovieDto create(MovieDto movieDto) {
        return movieRepository.save(movieDto);
    }
    public MovieDto update(long id,UpdateMovieDto movieDto) {
        return movieRepository.update(id,movieDto);
    }
    public void delete(long id) {
        this.movieRepository.delete(id);
    }
}
