package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.PessoaRankingResponse;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cwi.crescer.api.mapper.PessoaRankingMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PessoaRankingMapperTest {
    @Test
    @DisplayName("Deve gerar um response")
    void deveGerarUmRanking(){
        Usuario usuario = UsuarioFactory.get();
        usuario.setPontuacao("100");
        usuario.setImagemPerfil("Imagem de perfil");

        PessoaRankingResponse response = toResponse(usuario);

        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getPontuacao(), response.getPontos());
        assertEquals(usuario.getImagemPerfil(), response.getFoto());
        assertEquals(usuario.getNome(), response.getNome());

    }
}
