package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.response.UsuarioPublicoResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.ExibirInformacoesPublicasService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExibirInformacoesPublicasServiceTest {
    @InjectMocks
    private ExibirInformacoesPublicasService tested;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve exibir as informaçoes publicas de um usuario que existe")
    void exibirInfosDeUmUsuario(){
        Usuario usuario = UsuarioFactory.get();

        when(buscarUsuarioService.porId(usuario.getId())).thenReturn(usuario);
        when(usuarioRepository.findPosicaoUsuario(usuario.getId())).thenReturn(1L);


        UsuarioPublicoResponse response = tested.exibir(usuario.getId());

        assertEquals(usuario.getId(), response.getId());
        assertEquals(1L, response.getPosicao());
    }

    @Test
    @DisplayName("Deve ter um erro quando usuario nao existir")
    void erroQuandoUsuarioNaoExistir(){
        Long idPesquisado = SimpleFactory.getRandomLong();

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(idPesquisado);

        assertThrows(ResponseStatusException.class, () -> {
            tested.exibir(idPesquisado);
        });
    }
}
