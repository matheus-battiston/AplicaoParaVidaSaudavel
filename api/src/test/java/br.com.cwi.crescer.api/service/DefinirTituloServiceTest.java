package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.factories.ConquistaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.BuscarConquistaDesbloqueadaService;
import br.com.cwi.crescer.api.security.service.DefinirTituloService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefinirTituloServiceTest {
    @InjectMocks
    private DefinirTituloService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BuscarConquistaDesbloqueadaService buscarConquistaDesbloqueadaService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @DisplayName("Deve definir um titulo ao usuario")
    void deveDefinirTitulo(){
        String recompensa = "String da recompensa";
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();
        conquista.setRecompensa(recompensa);
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setConquista(conquista);


        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarConquistaDesbloqueadaService.porIdConquista(usuario, conquista.getId())).thenReturn(conquistaUsuario);


        tested.definir(conquista.getId());

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        Usuario capturado = usuarioArgumentCaptor.getValue();

        assertEquals(recompensa, capturado.getTitulo());
    }

    @Test
    @DisplayName("Deve ter erro quando nao encontrar a conquista")
    void deveTerErro(){
        String recompensa = "String da recompensa";
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();
        conquista.setRecompensa(recompensa);
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setConquista(conquista);


        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarConquistaDesbloqueadaService).porIdConquista(usuario, conquista.getId());


        assertThrows(ResponseStatusException.class, () -> {
            tested.definir(conquista.getId());
        });

        verify(usuarioRepository, never()).save(any());
    }
}
