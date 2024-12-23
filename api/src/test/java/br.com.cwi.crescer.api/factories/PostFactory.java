package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Post;

import java.util.ArrayList;

public class PostFactory {

    public static Post get() {
        return Post.builder()
                .id(SimpleFactory.getRandomLong())
                .texto("teste texto")
                .usuario(UsuarioFactory.get())
                .privado(false)
                .usuariosCurtiram(new ArrayList<>())
                .comentariosPost(new ArrayList<>())
                .build();
    }



}
