package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static br.com.cwi.crescer.api.service.BuscarUsuarioService.NAO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarUsuarioServiceTest {
    @InjectMocks
    private BuscarUsuarioService tested;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve retornar usuario logado")
    void usuarioLogado(){
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        Usuario response = tested.logado();

        assertEquals(usuario.getId(), response.getId());
    }

    @Test
    @DisplayName("Deve procurar um usuario por id")
    void deveProcurarUsuarioPorId(){
        Usuario usuario = UsuarioFactory.get();

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        Usuario response = tested.porId(usuario.getId());

        assertEquals(usuario.getId(), response.getId());
    }

    @Test
    @DisplayName("Deve retornar erro quando nao encontrar")
    void deveTerErroQuandoNaoEncontrar(){

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(SimpleFactory.getRandomLong()));

        assertEquals(NAO_ENCONTRADO, exception.getReason());


    }
}
