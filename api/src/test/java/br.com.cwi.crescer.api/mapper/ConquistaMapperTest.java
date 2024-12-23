package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ConquistasObtidasResponse;
import br.com.cwi.crescer.api.controller.response.ConquistasProgressoResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.factories.ConquistaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConquistaMapperTest {
    @Test
    @DisplayName("Deve mapear uma conquista para conquista usuario")
    void conquistaParaConquistaUsuario(){
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();

        ConquistaUsuario response = ConquistaMapper.toConquistaUsuario(conquista, usuario);

        assertEquals(usuario.getId(), response.getUsuario().getId());
        assertFalse(response.isDesbloqueada());
        assertEquals(conquista.getId(), response.getConquista().getId());
        assertEquals(0L, response.getProgresso());
    }

    @Test
    @DisplayName("Deve mapear corretamente uma conquistaUsuario para apresentar o progresso")
    void deveMapearUmaConquistaParaConquistaProgresso(){
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();

        ConquistaUsuario mapeado = ConquistaMapper.toConquistaUsuario(conquista, usuario);
        ConquistasProgressoResponse response = ConquistaMapper.toResponse(mapeado);

        assertEquals(conquista.getValor(), response.getObjetivo());
        assertEquals(conquista.getDescricao(), response.getDescricao());
        assertEquals(conquista.getCategoria(), response.getCategoria());
        assertFalse(response.isDesbloqueada());
        assertEquals(mapeado.getProgresso(), response.getProgresso());

    }

    @Test
    @DisplayName("Deve mapear corretamente uma conquista para o response de quando foi conquistada")
    void deveMapearParaResponseDeQuandoFoiConquistada(){
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();

        ConquistaUsuario mapeado = ConquistaMapper.toConquistaUsuario(conquista, usuario);
        mapeado.setDataAlteracao(LocalDate.now());

        ConquistasObtidasResponse response = ConquistaMapper.toResponseConquistado(mapeado);

        assertEquals(mapeado.getDataAlteracao(), response.getDataConquista());
        assertEquals(mapeado.getConquista().getDescricao(), response.getDescricao());
        assertEquals(mapeado.getConquista().getCategoria(), response.getCategoria());

    }
}
