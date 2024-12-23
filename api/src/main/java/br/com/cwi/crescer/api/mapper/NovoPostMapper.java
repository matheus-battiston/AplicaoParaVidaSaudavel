package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.NovoPostRequest;
import br.com.cwi.crescer.api.domain.Post;

public class NovoPostMapper {
    public static Post toEntity(NovoPostRequest request) {
        return Post.builder()
                .texto(request.getTexto())
                .privado(request.isPrivado())
                .imagem(request.getUrl())
                .build();
    }
}
