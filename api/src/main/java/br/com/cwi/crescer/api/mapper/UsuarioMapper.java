package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;

import java.util.Objects;

public class UsuarioMapper {
    public static UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .informado(Objects.nonNull(usuario.getAltura()))
                .foto(usuario.getImagemPerfil())
                .build();
    }
}
