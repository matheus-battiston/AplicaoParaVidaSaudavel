package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.controller.response.InformacoesPrivadasUsuarioResponse;
import br.com.cwi.crescer.api.security.domain.AtividadeFisica;
import br.com.cwi.crescer.api.security.domain.Meta;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.mapper.InformacoesPrivadasUsuarioMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InformacoesPrivadasUsuarioMapperTest {
    @Test
    @DisplayName("deve mapear corretamente um usuario")
    void deveMapearUmUsuario(){
        Usuario usuario = UsuarioFactory.get();
        usuario.setDataNascimento(LocalDate.of(1997, 9, 17));
        usuario.setAltura(170);
        usuario.setPeso("100");
        usuario.setImagemPerfil("IMAGEM PERFIL");
        usuario.setImc("IMC");
        usuario.setGastoCalorico(400);
        usuario.setAguaRecomendacao(1);
        usuario.setCaloriasRecomendacao(1800);
        usuario.setCarboidratosRecomendacao(300);
        usuario.setLipidiosRecomendacao(123);
        usuario.setMeta(Meta.M);
        usuario.setAtividadeFisica(AtividadeFisica.I);

        InformacoesPrivadasUsuarioResponse response = InformacoesPrivadasUsuarioMapper.toResponse(usuario);

        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getNome(), response.getNome());
        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(usuario.getDataNascimento(), response.getDataNascimento());
        assertEquals(usuario.getAltura(), response.getAltura());
        assertEquals(usuario.getPeso(), response.getPeso());
        assertEquals(usuario.getImagemPerfil(), response.getImagemPerfil());
        assertEquals(usuario.getImc(), response.getImc());
        assertEquals(usuario.getGastoCalorico(), response.getGastoCalorico());
        assertEquals(usuario.getAguaRecomendacao(), response.getAguaRecomendacao());
        assertEquals(usuario.getCaloriasRecomendacao(), response.getCaloriasRecomendacao());
        assertEquals(usuario.getCarboidratosRecomendacao(), response.getCarboidratosRecomendacao());
        assertEquals(usuario.getLipidiosRecomendacao(), response.getLipidiosRecomendacao());
        assertEquals(usuario.getMeta(), response.getMeta());
        assertEquals(usuario.getAtividadeFisica(), response.getAtividadeFisica());
        assertEquals(0, response.getNroSeguidores());
        assertEquals(usuario.getPontuacao(), response.getPontuacao());


    }
}
