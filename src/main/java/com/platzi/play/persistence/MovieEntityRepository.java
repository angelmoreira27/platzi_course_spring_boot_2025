package com.platzi.play.persistence;

import com.platzi.play.domain.exception.MovieAlreadyExistsException;
import com.platzi.play.domain.dto.MovieDto;
import com.platzi.play.domain.dto.UpdateMovieDto;
import com.platzi.play.domain.exception.MovieNotFoundException;
import com.platzi.play.domain.repository.MovieRepository;
import com.platzi.play.persistence.crud.CrudMovieEntity;
import com.platzi.play.persistence.entity.MovieEntity;
import com.platzi.play.persistence.mapper.MovieMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class MovieEntityRepository implements MovieRepository {

    private final CrudMovieEntity crudMovieEntity;
    private final MovieMapper movieMapper;

    public MovieEntityRepository(CrudMovieEntity crudMovieEntity, MovieMapper movieMapper) {
        this.crudMovieEntity = crudMovieEntity;
        this.movieMapper= movieMapper;
    }

    @Override
    public List<MovieDto> getAll() {
        return movieMapper.toDto(this.crudMovieEntity.findAll());
    }

    @Override
    public MovieDto getById(Long id) {
       MovieEntity movieEntity=this.crudMovieEntity.findById(id).orElse(null);
        return movieMapper.toDto(movieEntity);
    }

    @Override
    public MovieDto save(MovieDto movieDto) {

      if(this.crudMovieEntity.findFirstByTitulo(movieDto.title())!=null){
            throw new MovieAlreadyExistsException(movieDto.title());
      }


        MovieEntity movieEntity=this.movieMapper.toEntity(movieDto);
        movieEntity.setEstado("D");
        return  this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public MovieDto update(long id,UpdateMovieDto updatemovieDto) {

        MovieEntity movientity=this.crudMovieEntity.findById(id).orElse(null);

         if (movientity==null) {
             throw new  MovieNotFoundException("No se encontró pelicula");
        }

        this.movieMapper.updateEntityFromDto(updatemovieDto,movientity);
        return this.movieMapper.toDto(this.crudMovieEntity.save(movientity));
    }

    @Override
    public void delete(long id) {
        MovieEntity Entity=this.crudMovieEntity.findById(id).orElse(null);
        if (Entity==null) {
            throw new  MovieNotFoundException("No se encontró pelicula");
        }

        this.crudMovieEntity.deleteById(id);
    }
}
