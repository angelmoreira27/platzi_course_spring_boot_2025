package com.platzi.play.domain.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface PlatziPlayAiService {

    @UserMessage("""
            Genera un salido de bienvenida a la plataforma de Gestión de películas {{plataform}}.
            Usa menos de 120 caracteres y hazlo con el estilo de Platzi
            """)
    //se usa el @V para validar con que nombre se tomara, ojo esto es por la estructura langchai4j
    String generateGreeting(@V("plataform") String plataform);

    @SystemMessage("""
            Eres un experto en cine que recomienda películas personalizadas según los gustos del usuario.
            Debes recomendar maxico 3 peliculas.
            No incluyas que esten por fuera de la plataforma de {{plataform}}
            """)
        //se usa el @V para validar con que nombre se tomara, ojo esto es por la estructura langchai4j
    String generateMoviesSuggestion(@UserMessage String userMessage,@V("plataform") String plataform);
}
