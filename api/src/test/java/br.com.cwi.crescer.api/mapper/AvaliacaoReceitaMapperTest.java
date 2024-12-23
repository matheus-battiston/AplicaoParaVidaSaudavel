package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.AvaliacaoReceitaRequest;
import br.com.cwi.crescer.api.domain.AvaliacaoReceita;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvaliacaoReceitaMapperTest {
    @Test
    @DisplayName("Deve mapear corretamente um request para entidade")
    public void deveMapearUmRequestParaEntidade(){
        AvaliacaoReceitaRequest avaliacaoReceitaRequest = new AvaliacaoReceitaRequest();
        Usuario avaliador = UsuarioFactory.get();
        avaliacaoReceitaRequest.setNota("5");


        AvaliacaoReceita response = AvaliacaoReceitaMapper.toEntity(avaliador, parseInt(avaliacaoReceitaRequest.getNota()));

        assertEquals(parseInt(avaliacaoReceitaRequest.getNota()), response.getNota());
        assertEquals(avaliador, response.getUsuario());
    }
}
