package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.domain.Mensagem;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.controller.response.MensagemResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static br.com.cwi.crescer.api.security.mapper.MensagemMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MensagemMapperTest {
    @Test
    @DisplayName("Deve mapear corretamente uma mensagem para o response")
    void deveMapearMensagemParaResponse(){
        Usuario usuario = UsuarioFactory.get();
        Usuario usuario2 = UsuarioFactory.get();

        Conversa conversa = new Conversa();
        conversa.setId(getRandomLong());
        conversa.setPrimeiroUsuario(usuario);
        conversa.setSegundoUsuario(usuario2);

        Mensagem entity = new Mensagem();
        entity.setConversa(conversa);
        entity.setTexto("texto teste");
        entity.setRemetente(usuario);
        entity.setId(getRandomLong());

        MensagemResponse response = toResponse(entity);

        assertEquals(entity.getRemetente().getId(), response.getIdRemetente());
        assertEquals(entity.getTexto(), response.getMensagem());
    }

}
