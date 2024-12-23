package br.com.cwi.crescer.api.security.mapper;

import br.com.cwi.crescer.api.security.controller.response.UsuarioPublicoResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;

import java.util.stream.Collectors;

public class UsuarioPublicoMapper {
    public static UsuarioPublicoResponse toResponse(Usuario procurado) {
        return UsuarioPublicoResponse.builder()
                .foto(procurado.getImagemPerfil())
                .nome(procurado.getNome())
                .idSeguidores(procurado.getSeguidores().stream().map(Usuario::getId).collect(Collectors.toList()))
                .idSeguindo(procurado.getSeguindo().stream().map(Usuario::getId).collect(Collectors.toList()))
                .pontos(procurado.getPontuacao())
                .titulo(procurado.getTitulo())
                .id(procurado.getId())
                .categoriaTag(procurado.getCategoriaTitulo())
                .build();
    }

}
