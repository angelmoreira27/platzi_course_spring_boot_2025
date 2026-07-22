package com.platzi.play.persistence.mapper;

import com.platzi.play.domain.dto.MovieDto;
import com.platzi.play.domain.dto.UpdateMovieDto;
import com.platzi.play.persistence.entity.MovieEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring",uses = {GenreMapper.class})
public interface MovieMapper {

    //esto se utiliza para trasnformar datos que no contienen los mismos tipos de titulos
    //y se utilicen para transformarlos de mejor manera
    //en este ejemplo estamos dano un dato MovieDto y lo transformamos a movieEntity
    //lo mismo con la lista
    //adicional como ambos metodo utilizan los mismos datos no se repiten
    @Mapping(source = "titulo",target = "title")
    @Mapping(source = "duracion",target = "duration")
    @Mapping(source = "genero",target = "genre",qualifiedByName = "stringToGenre")
    @Mapping(source = "fechaEstreno",target = "releaseDate")
    @Mapping(source = "clasificacion",target = "rating")
    MovieDto toDto(MovieEntity movieEntity);
    //aqui se utiliza Iterable ya que el MovieEntity es una clase con anotacion Entity y sus tipos de datos
    //en una lista son de tipo iterator
    List<MovieDto> toDto(Iterable<MovieEntity> entities);

    //utliza las mismas variables arriba delcaradas pero de forma inversa
    //el source sera target y el target source
    @InheritInverseConfiguration
    @Mapping(source = "genre",target = "genero",qualifiedByName = "GenreTostring")
    MovieEntity toEntity(MovieDto dto);

    @Mapping(source = "title",target = "titulo")
    @Mapping(source = "releaseDate",target = "fechaEstreno")
    @Mapping(source = "rating",target = "clasificacion")
    void updateEntityFromDto(UpdateMovieDto dto,@MappingTarget MovieEntity movieEntity);


}
