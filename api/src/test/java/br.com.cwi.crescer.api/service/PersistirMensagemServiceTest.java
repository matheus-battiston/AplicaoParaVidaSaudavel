package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.model.MensagemModel;
import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.domain.Mensagem;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.repository.MensagemRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.cwi.crescer.api.controller.model.Status.MESSAGE;
import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static br.com.cwi.crescer.api.factories.UsuarioFactory.get;
import static br.com.cwi.crescer.api.service.PersistirMensagemService.MENSAGEM_CONVERSA_NAO_EXISTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class PersistirMensagemServiceTest {
    @InjectMocks
    private PersistirMensagemService persistirMensagemService;

    @Mock
    private MensagemRepository mensagemRepository;

    @Mock
    private ConversaRepository conversaRepository;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Captor
    private ArgumentCaptor<Mensagem> mensagemArgumentCaptor;

    @Test
    @DisplayName("Deve persistir uma mensagem corretamente se o remetente for o primeiro usuario")
    void devePersistirMensagemRemetentePrimeiroUsuario(){
        Usuario remetente = get();
        Usuario destinatario = get();

        Conversa conversa = new Conversa();
        conversa.setId(getRandomLong());
        conversa.setPrimeiroUsuario(remetente);
        conversa.setSegundoUsuario(destinatario);

        List<Conversa> conversasRemetente = new ArrayList<>();
        conversasRemetente.add(conversa);

        MensagemModel mensagemModel = new MensagemModel();
        mensagemModel.setDate(String.valueOf(LocalDate.now()));
        mensagemModel.setStatus(MESSAGE);
        mensagemModel.setSenderId(String.valueOf(remetente.getId()));
        mensagemModel.setReceiverId(String.valueOf(destinatario.getId()));
        mensagemModel.setMessage("mensagem de teste");

        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getSenderId()))).thenReturn(remetente);
        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getReceiverId()))).thenReturn(destinatario);
        when(conversaRepository
                .findAllByPrimeiroUsuarioOrSegundoUsuario(remetente, remetente)).thenReturn(conversasRemetente);

        persistirMensagemService.persistir(mensagemModel);

        verify(mensagemRepository).save(mensagemArgumentCaptor.capture());

        Mensagem mensagem = mensagemArgumentCaptor.getValue();

        assertEquals(remetente, mensagem.getRemetente());
        assertEquals(mensagemModel.getMessage(), mensagem.getTexto());
        assertEquals(conversa, mensagem.getConversa());
    }

    @Test
    @DisplayName("Deve persistir uma mensagem corretamente se o remetente for o segundo usuário")
    void devePersistirMensagem(){
        Usuario remetente = get();
        Usuario destinatario = get();

        Conversa conversa = new Conversa();
        conversa.setId(getRandomLong());
        conversa.setPrimeiroUsuario(destinatario);
        conversa.setSegundoUsuario(remetente);

        List<Conversa> conversasRemetente = new ArrayList<>();
        conversasRemetente.add(conversa);

        MensagemModel mensagemModel = new MensagemModel();
        mensagemModel.setDate(String.valueOf(LocalDate.now()));
        mensagemModel.setStatus(MESSAGE);
        mensagemModel.setSenderId(String.valueOf(remetente.getId()));
        mensagemModel.setReceiverId(String.valueOf(destinatario.getId()));
        mensagemModel.setMessage("mensagem de teste");

        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getSenderId()))).thenReturn(remetente);
        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getReceiverId()))).thenReturn(destinatario);
        when(conversaRepository
                .findAllByPrimeiroUsuarioOrSegundoUsuario(remetente, remetente)).thenReturn(conversasRemetente);

        persistirMensagemService.persistir(mensagemModel);

        verify(mensagemRepository).save(mensagemArgumentCaptor.capture());

        Mensagem mensagem = mensagemArgumentCaptor.getValue();

        assertEquals(remetente, mensagem.getRemetente());
        assertEquals(mensagemModel.getMessage(), mensagem.getTexto());
        assertEquals(conversa, mensagem.getConversa());
    }

    @Test
    @DisplayName("Deve retornar ResponseStatusException se a conversa entre os usuários não existe")
    void deveRetornarErroSeConversaNaoExiste(){
        Usuario remetente = get();
        Usuario destinatario = get();

        MensagemModel mensagemModel = new MensagemModel();
        mensagemModel.setDate(String.valueOf(LocalDate.now()));
        mensagemModel.setStatus(MESSAGE);
        mensagemModel.setSenderId(String.valueOf(remetente.getId()));
        mensagemModel.setReceiverId(String.valueOf(destinatario.getId()));
        mensagemModel.setMessage("mensagem de teste");

        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getSenderId()))).thenReturn(remetente);
        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getReceiverId()))).thenReturn(destinatario);
        when(conversaRepository
                .findAllByPrimeiroUsuarioOrSegundoUsuario(remetente, remetente)).thenReturn(new ArrayList<>());

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> persistirMensagemService.persistir(mensagemModel));

        assertEquals(MENSAGEM_CONVERSA_NAO_EXISTE, exception.getReason());
    }

}
