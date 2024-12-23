package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.ComentarioPost;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;

public class ComentarioFactory {
    public static ComentarioPost get() {
        return ComentarioPost.builder()
                .id(getRandomLong())
                .texto("Texto do comentario")
                .build();
    }
}
