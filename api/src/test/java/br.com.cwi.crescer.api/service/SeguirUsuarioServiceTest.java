package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.SeguirUsuarioService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.security.validator.ValidatorJaSegue;
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
public class SeguirUsuarioServiceTest {
    @InjectMocks
    private SeguirUsuarioService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ValidatorJaSegue validatorJaSegue;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @DisplayName("Deve seguir um usuario")
    void deveSeguirUsuario(){
        Usuario usuario = UsuarioFactory.get();
        Usuario seguido = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarUsuarioService.porId(seguido.getId())).thenReturn(seguido);

        tested.seguir(seguido.getId());

        verify(validatorJaSegue).validarNaoSegue(usuario, seguido);
        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());

        Usuario usuarioSalvo = usuarioArgumentCaptor.getValue();
        assertEquals(usuarioSalvo.getSeguindo().size(), 1);
        assertEquals(usuarioSalvo.getSeguindo().get(0).getId(), seguido.getId());
        assertEquals(usuarioSalvo.getSeguindo().get(0).getSeguidores().size(), 1);
        assertEquals(usuarioSalvo.getSeguindo().get(0).getSeguidores().get(0).getId(), usuario.getId());

    }

    @Test
    @DisplayName("Deve lançar um erro quando tentar seguir usuario ja seguido")
    void deveRetornarErroQuandoUsuarioJaForSeguido(){
        Usuario usuario = UsuarioFactory.get();
        Usuario seguido = UsuarioFactory.get();
        usuario.seguirUsuario(seguido);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarUsuarioService.porId(seguido.getId())).thenReturn(seguido);

        doThrow(ResponseStatusException.class).when(validatorJaSegue).validarNaoSegue(usuario, seguido);

        assertThrows(ResponseStatusException.class, () -> {
            tested.seguir(seguido.getId());
        });

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve ter um erro quando tentar seguir usuario que nao existe")
    void erroQuandoUsuarioNaoExiste(){
        Usuario usuario = UsuarioFactory.get();
        Long seguidoId = SimpleFactory.getRandomLong();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(seguidoId);

        assertThrows(ResponseStatusException.class, () -> {
            tested.seguir(seguidoId);
        });

        verify(usuarioRepository, never()).save(any());
    }
}
