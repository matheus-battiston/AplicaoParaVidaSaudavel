package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.domain.Mensagem;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.repository.MensagemRepository;
import br.com.cwi.crescer.api.security.controller.response.MensagemResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.ListarMensagensService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarMensagensServiceTest {
    @InjectMocks
    private ListarMensagensService listarMensagensService;

    @Mock
    private ConversaRepository conversaRepository;

    @Mock
    private MensagemRepository mensagemRepository;

    @Test
    @DisplayName("Deve listar as mensagens do usuário corretamente")
    void deveListarMensagensUsuario(){
        Usuario usuario1 = UsuarioFactory.get();
        Usuario usuario2 = UsuarioFactory.get();

        Optional<Conversa> conversa = Optional.of(new Conversa());
        conversa.get().setId(getRandomLong());
        conversa.get().setPrimeiroUsuario(usuario1);
        conversa.get().setSegundoUsuario(usuario2);

        Mensagem mensagem = new Mensagem();
        mensagem.setRemetente(usuario1);
        mensagem.setConversa(conversa.get());
        mensagem.setTexto("teste");
        mensagem.setId(getRandomLong());

        List<Mensagem> mensagens = new ArrayList<>();
        mensagens.add(mensagem);

        when(conversaRepository.findById(conversa.get().getId())).thenReturn(conversa);
        when(mensagemRepository.findAllByConversa(conversa.get())).thenReturn(mensagens);

        List<MensagemResponse> response = listarMensagensService.listar(conversa.get().getId());

        assertEquals(mensagem.getRemetente().getId(), response.get(0).getIdRemetente());
        assertEquals(mensagem.getTexto(), response.get(0).getMensagem());


    }

}
