package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.PessoaRankingResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;

public class PessoaRankingMapper {
    public static PessoaRankingResponse toResponse(Usuario usuario) {
        return PessoaRankingResponse.builder()
                .nome(usuario.getNome())
                .foto(usuario.getImagemPerfil())
                .pontos(usuario.getPontuacao())
                .id(usuario.getId())
                .build();
    }
}
