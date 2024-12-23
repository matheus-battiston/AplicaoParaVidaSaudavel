package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.security.controller.response.ConversaResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.ListarConversasService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarConversasServiceTest {
    @InjectMocks
    private ListarConversasService listarConversasService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ConversaRepository conversaRepository;

    @Test
    @DisplayName("Deve listar as conversas do usuário corretamente")
    void deveListarConversasUsuario(){
        Usuario usuario = UsuarioFactory.get();
        Usuario usuario2 = UsuarioFactory.get();

        Conversa conversa = new Conversa();
        conversa.setPrimeiroUsuario(usuario);
        conversa.setSegundoUsuario(usuario2);

        List<Conversa> conversasUsuario = new ArrayList<>();
        conversasUsuario.add(conversa);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(conversaRepository
                .findAllByPrimeiroUsuarioOrSegundoUsuario(usuario, usuario)).thenReturn(conversasUsuario);

        List<ConversaResponse> response = listarConversasService.listar();

        assertEquals(conversa.getSegundoUsuario().getId(), response.get(0).getIdUsuario());
    }
}
