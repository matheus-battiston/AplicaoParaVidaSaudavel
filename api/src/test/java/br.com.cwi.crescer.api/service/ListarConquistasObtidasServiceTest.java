package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ConquistasObtidasResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.factories.ConquistaFactory;
import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.mapper.ConquistaMapper;
import br.com.cwi.crescer.api.repository.ConquistaUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ListarConquistasObtidasServiceTest {
    @InjectMocks
    private ListarConquistasObtidasService tested;
    @Mock
    private ConquistaUsuarioRepository conquistaUsuarioRepository;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nao tem conquista obtida")
    void deveRetornarListaVazia(){
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();
        ConquistaUsuario mapeado = ConquistaMapper.toConquistaUsuario(conquista, usuario);

        when(buscarUsuarioService.porId(usuario.getId())).thenReturn(usuario);
        when(conquistaUsuarioRepository.findAllByUsuarioAndDesbloqueadaIsTrue(usuario)).thenReturn(List.of(mapeado));

        List<ConquistasObtidasResponse> response = tested.listar(usuario.getId());


        assertEquals(1, response.size());
        assertEquals(conquista.getDescricao(),response.get(0).getDescricao());
    }

    @Test
    @DisplayName("Deve retornar uma lista com as conquistas")
    void deveRetornarListaComConquistas(){
        Usuario usuario = UsuarioFactory.get();

        when(buscarUsuarioService.porId(usuario.getId())).thenReturn(usuario);
        List<ConquistasObtidasResponse> response = tested.listar(usuario.getId());

        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar um erro quando usuario nao existir")
    void deveRetornarErroQuandoUsuarioNaoExistir(){
        Long id = SimpleFactory.getRandomLong();

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(id);

        assertThrows(ResponseStatusException.class, () -> {
            tested.listar(id);
        });

    }
}
