package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ComentarioResponse;
import br.com.cwi.crescer.api.domain.ComentarioPost;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.security.domain.Usuario;

public class ComentarioPostMapper {

    public static ComentarioPost toEntity(String texto, Usuario usuario, Post post) {
        return ComentarioPost.builder()
                .post(post)
                .texto(texto)
                .usuario(usuario)
                .build();
    }

    public static ComentarioResponse toResponse(ComentarioPost comentarioPost) {
        return ComentarioResponse.builder()
                .id(comentarioPost.getId())
                .comentario(comentarioPost.getTexto())
                .usuario(ListarPessoaMapper.toResponse(comentarioPost.getUsuario()))
                .build();
    }
}
