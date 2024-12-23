package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.TagsResponse;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;

public class TagsResponseMapper {
    public static TagsResponse toResponse(ConquistaUsuario conquistaUsuario) {
        return  TagsResponse.builder()
                .tag(conquistaUsuario.getConquista().getRecompensa())
                .categoria(conquistaUsuario.getConquista().getCategoria())
                .idConquista(conquistaUsuario.getConquista().getId())
                .build();
    }
}
