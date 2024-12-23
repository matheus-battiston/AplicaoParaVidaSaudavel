package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListarPessoaResponse;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.mapper.ListarPessoaMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class ListarPessoaMapperTest {

    @Test
    @DisplayName("Deve mapear corretamente um usuario para o response")
    public void deveMapearUsuarioParaResponse(){
        Usuario usuario = UsuarioFactory.get();
        usuario.setImagemPerfil("Imagem perfil");

        ListarPessoaResponse response = toResponse(usuario);

        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getNome(), response.getNome());
        assertEquals(usuario.getImagemPerfil(), response.getFoto());
    }
}
