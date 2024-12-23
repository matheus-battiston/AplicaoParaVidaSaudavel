package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ComentarioResponse;
import br.com.cwi.crescer.api.domain.ComentarioPost;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.ComentarioFactory;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})

public class ListarComentariosPostServiceTest {
    @InjectMocks
    private ListarComentariosPostService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private BuscarPostService buscarPostService;

    @Test
    @DisplayName("Deve listar os comentarios de um post")
    public void deveListarOsComentarios(){
        Post post = PostFactory.get();
        Usuario usuario = UsuarioFactory.get();

        ComentarioPost comentarioUm = ComentarioFactory.get();
        comentarioUm.setUsuario(usuario);
        ComentarioPost comentarioDois = ComentarioFactory.get();
        comentarioDois.setUsuario(usuario);

        post.adicionarComentario(comentarioUm);
        post.adicionarComentario(comentarioDois);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarPostService.porIdComAcesso(post.getId(), usuario)).thenReturn(post);

        List<ComentarioResponse> response = tested.listar(post.getId());

        assertEquals(response.size(), 2);
        assertEquals(response.get(0).getId(), comentarioUm.getId());
        assertEquals(response.get(1).getId(), comentarioDois.getId());

    }

    @Test
    @DisplayName("Deve retornar uma lista vazia por nao ter comentarios")
    public void deveTerUmaListaVazia(){
        Post post = PostFactory.get();
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarPostService.porIdComAcesso(post.getId(), usuario)).thenReturn(post);

        List<ComentarioResponse> response = tested.listar(post.getId());

        assertTrue(response.isEmpty());
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
}
