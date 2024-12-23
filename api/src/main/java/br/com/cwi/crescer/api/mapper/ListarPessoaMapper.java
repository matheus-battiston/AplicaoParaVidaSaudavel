package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListarPessoaResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;

public class ListarPessoaMapper {
    public static ListarPessoaResponse toResponse(Usuario usuario) {
        return ListarPessoaResponse
                .builder()
                .id(usuario.getId())
                .foto(usuario.getImagemPerfil())
                .nome(usuario.getNome())
                .build();
    }
}
