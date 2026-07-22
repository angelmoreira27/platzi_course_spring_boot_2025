package com.platzi.play.domain.dto;

import com.platzi.play.domain.Genre;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MovieDto(
        Long id,
        @NotBlank(message = "El dato no puede estar en blanco")
        String title,
        @Min(value=0,message = "La duracion tiene que ser mayor a 0")
        Integer duration,
        @NotNull(message="El valor no puede estar en blanco")
        Genre genre,
        @PastOrPresent(message = "El valor tiene que se actual")
        LocalDate releaseDate,
        @Min(value=0,message = "La calificacion tiene que ser mayor a 0")
        @Max(value=5,message = "La calificacion tiene que ser menor a 5")
        Double rating
) {

}
