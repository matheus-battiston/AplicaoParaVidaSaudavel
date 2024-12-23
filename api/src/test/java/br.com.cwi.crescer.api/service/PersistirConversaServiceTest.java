package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.model.MensagemModel;
import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static br.com.cwi.crescer.api.controller.model.Status.MESSAGE;
import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static br.com.cwi.crescer.api.factories.UsuarioFactory.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class PersistirConversaServiceTest {
    @InjectMocks
    private PersistirConversaService persistirConversaService;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private ConversaRepository conversaRepository;

    @Captor
    private ArgumentCaptor<Conversa> conversaArgumentCaptor;

    @Test
    @DisplayName("Deve persistir uma conversa corretamente")
    void devePersistirConversa(){
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
                .existsByPrimeiroUsuarioAndSegundoUsuario(remetente, destinatario)).thenReturn(false);
        when(conversaRepository
                .existsByPrimeiroUsuarioAndSegundoUsuario(destinatario, remetente)).thenReturn(false);

        persistirConversaService.persistir(mensagemModel);

        verify(conversaRepository).save(conversaArgumentCaptor.capture());

        Conversa conversa = conversaArgumentCaptor.getValue();

        assertEquals(remetente, conversa.getPrimeiroUsuario());
        assertEquals(destinatario, conversa.getSegundoUsuario());
    }

    @Test
    @DisplayName("Não deve persistir uma conversa que já existe")
    void naoDevePersistirConversaQueJaExiste(){
        Usuario remetente = get();
        Usuario destinatario = get();
        Conversa conversa = new Conversa();
        conversa.setId(getRandomLong());
        conversa.setPrimeiroUsuario(remetente);
        conversa.setSegundoUsuario(destinatario);
        MensagemModel mensagemModel = new MensagemModel();
        mensagemModel.setDate(String.valueOf(LocalDate.now()));
        mensagemModel.setStatus(MESSAGE);
        mensagemModel.setSenderId(String.valueOf(remetente.getId()));
        mensagemModel.setReceiverId(String.valueOf(destinatario.getId()));
        mensagemModel.setMessage("mensagem de teste");
        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getSenderId()))).thenReturn(remetente);
        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getReceiverId()))).thenReturn(destinatario);
        when(conversaRepository
                .existsByPrimeiroUsuarioAndSegundoUsuario(remetente, destinatario)).thenReturn(true);
        when(conversaRepository
                .existsByPrimeiroUsuarioAndSegundoUsuario(destinatario, remetente)).thenReturn(false);

        persistirConversaService.persistir(mensagemModel);

        verify(conversaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Não deve persistir uma conversa entre o usuário e ele mesmo")
    void naoDevePersistirConversaEntreUsuarioEEleMesmo(){
        Usuario remetente = get();

        Conversa conversa = new Conversa();
        conversa.setId(getRandomLong());
        conversa.setPrimeiroUsuario(remetente);
        conversa.setSegundoUsuario(remetente);

        MensagemModel mensagemModel = new MensagemModel();
        mensagemModel.setDate(String.valueOf(LocalDate.now()));
        mensagemModel.setStatus(MESSAGE);
        mensagemModel.setSenderId(String.valueOf(remetente.getId()));
        mensagemModel.setReceiverId(String.valueOf(remetente.getId()));
        mensagemModel.setMessage("mensagem de teste");

        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getSenderId()))).thenReturn(remetente);
        when(buscarUsuarioService.porId(Long.valueOf(mensagemModel.getReceiverId()))).thenReturn(remetente);
        when(conversaRepository
                .existsByPrimeiroUsuarioAndSegundoUsuario(remetente, remetente)).thenReturn(false);
        when(conversaRepository
                .existsByPrimeiroUsuarioAndSegundoUsuario(remetente, remetente)).thenReturn(false);

        persistirConversaService.persistir(mensagemModel);

        verify(conversaRepository, never()).save(any());
    }

}
