package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.controller.response.ConversaResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static br.com.cwi.crescer.api.security.mapper.ConversaMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConversaMapperTest {
    @Test
    @DisplayName("Deve mapear uma conversa corretamente para response")
    void deveMapearConversaParaResponse(){
        Usuario usuario1 = UsuarioFactory.get();
        Usuario usuario2 = UsuarioFactory.get();

        Conversa conversa = new Conversa();
        conversa.setId(getRandomLong());
        conversa.setPrimeiroUsuario(usuario1);
        conversa.setSegundoUsuario(usuario2);

        ConversaResponse response = toResponse(usuario1, conversa);

        assertEquals(conversa.getId(), response.getIdConversa());
        assertEquals(usuario1.getNome(), response.getNome());
        assertEquals(usuario1.getImagemPerfil(), response.getFoto());
        assertEquals(usuario1.getId(), response.getIdUsuario());
    }

}
