package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.AdicionarConversaService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.security.service.ValidaConversaJaExisteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdicionarConversaServiceTest {
    @InjectMocks
    private AdicionarConversaService adicionarConversaService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private ConversaRepository conversaRepository;

    @Mock
    private ValidaConversaJaExisteService validaConversaJaExisteService;

    @Captor
    private ArgumentCaptor<Conversa> conversaArgumentCaptor;

    @Test
    @DisplayName("Deve adicionar uma conversa corretamente")
    void deveAdicionarConversaCorretamente(){
        Usuario usuarioAutenticado = UsuarioFactory.get();
        Usuario usuarioAAdicionar = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuarioAutenticado);
        when(buscarUsuarioService.porId(usuarioAAdicionar.getId())).thenReturn(usuarioAAdicionar);

        adicionarConversaService.adicionar(usuarioAAdicionar.getId());

        verify(validaConversaJaExisteService).validar(usuarioAutenticado, usuarioAAdicionar);
        verify(conversaRepository).save(conversaArgumentCaptor.capture());

        Conversa conversa = conversaArgumentCaptor.getValue();

        assertEquals(usuarioAutenticado, conversa.getPrimeiroUsuario());
        assertEquals(usuarioAAdicionar, conversa.getSegundoUsuario());
    }
}
