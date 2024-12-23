package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.controller.response.UsuarioPublicoResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.mapper.UsuarioPublicoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioPublicoMapperTest {
    @Test
    @DisplayName("Deve mapear corretamente um usuario para response")
    void mapearUsuarioParaResponse(){
        Usuario usuario = UsuarioFactory.get();
        List<Usuario> seguindo =List.of(UsuarioFactory.get(), UsuarioFactory.get());
        List<Usuario> seguidores  =List.of(UsuarioFactory.get(), UsuarioFactory.get());

        usuario.setSeguindo(seguindo);
        usuario.setSeguidores(seguidores);

        usuario.setImagemPerfil("Imagem de perfil");
        usuario.setPontuacao("1000");

        UsuarioPublicoResponse response = UsuarioPublicoMapper.toResponse(usuario);

        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getImagemPerfil(), response.getFoto());
        assertEquals(usuario.getNome(), response.getNome());
        assertEquals(usuario.getPontuacao(), response.getPontos());
        assertEquals(usuario.getTitulo(), response.getTitulo());
        assertEquals(2, response.getIdSeguidores().size());
    }
}
