package com.platzi.play.domain.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String dato) {
        super("La pelicula no existe: " + dato);
    }
}
