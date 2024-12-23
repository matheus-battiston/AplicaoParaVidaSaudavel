package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ConquistasObtidasResponse;
import br.com.cwi.crescer.api.controller.response.ConquistasProgressoResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.security.domain.Usuario;

public class ConquistaMapper {
    public static ConquistaUsuario toConquistaUsuario(Conquista conquista, Usuario usuario) {
        return ConquistaUsuario.builder()
                .usuario(usuario)
                .conquista(conquista)
                .desbloqueada(false)
                .progresso(0L)
                .build();
    }

    public static ConquistasProgressoResponse toResponse(ConquistaUsuario conquistaUsuario) {
        return ConquistasProgressoResponse.builder()
                .id(conquistaUsuario.getId())
                .descricao(conquistaUsuario.getConquista().getDescricao())
                .objetivo(conquistaUsuario.getConquista().getValor())
                .progresso(conquistaUsuario.getProgresso())
                .categoria(conquistaUsuario.getConquista().getCategoria())
                .desbloqueada(conquistaUsuario.isDesbloqueada())
                .recompensa(conquistaUsuario.getConquista().getRecompensa())
                .idConquista(conquistaUsuario.getConquista().getId())
                .build();
    }

    public static ConquistasObtidasResponse toResponseConquistado(ConquistaUsuario conquistaUsuario) {
        return ConquistasObtidasResponse.builder()
                .dataConquista(conquistaUsuario.getDataAlteracao())
                .descricao(conquistaUsuario.getConquista().getDescricao())
                .categoria(conquistaUsuario.getConquista().getCategoria())
                .build();
    }
}
