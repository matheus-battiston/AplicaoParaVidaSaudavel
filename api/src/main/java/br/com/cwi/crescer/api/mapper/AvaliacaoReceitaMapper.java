package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.domain.AvaliacaoReceita;
import br.com.cwi.crescer.api.security.domain.Usuario;

public class AvaliacaoReceitaMapper {
    public static AvaliacaoReceita toEntity(Usuario usuario, int nota) {
        return AvaliacaoReceita.builder()
                .nota(nota)
                .usuario(usuario)
                .build();
    }

}
