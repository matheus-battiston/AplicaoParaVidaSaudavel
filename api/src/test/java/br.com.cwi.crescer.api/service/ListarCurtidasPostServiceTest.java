package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListarCurtidasPostResponse;
import br.com.cwi.crescer.api.controller.response.ListarPessoaResponse;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.PostFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})

public class ListarCurtidasPostServiceTest {
    @InjectMocks
    private ListarCurtidasPostService tested;

    @Mock
    private BuscarPostService buscarPostService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve listar as curtidas de um post")
    public void deveListarCurtidasDoPost(){
        Usuario usuarioUm = UsuarioFactory.get();
        Usuario usuarioDois = UsuarioFactory.get();
        Post post = PostFactory.get();
        post.adicionarCurtidaUsuario(usuarioUm);
        post.adicionarCurtidaUsuario(usuarioDois);

        when(usuarioAutenticadoService.get()).thenReturn(usuarioUm);
        when(buscarPostService.porIdComAcesso(post.getId(), usuarioUm)).thenReturn(post);

        ListarCurtidasPostResponse response = tested.listar(post.getId());
        List<ListarPessoaResponse> responseUsuarios = response.getPessoasCurtida();


        assertEquals(responseUsuarios.size(), 2);
        assertEquals(responseUsuarios.get(0).getId(), usuarioUm.getId());
        assertEquals(responseUsuarios.get(1).getId(), usuarioDois.getId());

    }

    @Test
    @DisplayName("Deve ter um erro quando post nao existir")
    public void deveTerErroSePostNaoExiste(){
        Long idPost = getRandomLong();
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        doThrow(ResponseStatusException.class).when(buscarPostService).porIdComAcesso(idPost, usuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.listar(idPost);
        });
    }

    @Test
    @DisplayName("Deve retornar um erro quando usuario nao tem acesso ao post")
    void deveRetornarErroQuandoNaoTemAcesso(){
        Post post = PostFactory.get();
        post.setPrivado(true);
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarPostService).porIdComAcesso(post.getId(), usuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.listar(post.getId());
        });
    }
}
