package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.PesoResponse;
import br.com.cwi.crescer.api.domain.Peso;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PesoMapperTest {

    @Test
    @DisplayName("Deve mapear um request de peso inicial corretamente para entidade")
    void pesoInicialParaEntidade(){
        String peso = "100";
        LocalDate hoje = LocalDate.now();
        Usuario usuario = UsuarioFactory.get();

        Peso novoPeso = PesoMapper.toEntity(peso, hoje, usuario);

        assertEquals(peso, novoPeso.getValor());
        assertEquals(hoje, novoPeso.getDataRegistro());
        assertEquals(usuario.getId(), novoPeso.getUsuario().getId());
    }

    @Test
    @DisplayName("Deve mapear corretamente para um response")
    void pesoParaResponse(){
        String valorPeso = "100";
        LocalDate data = LocalDate.now();
        Peso peso = new Peso();
        peso.setValor(valorPeso);
        peso.setDataRegistro(data);

        PesoResponse response = PesoMapper.toResponse(peso);

        assertEquals(valorPeso, response.getPeso());
        assertEquals(data, response.getData());
    }
}
