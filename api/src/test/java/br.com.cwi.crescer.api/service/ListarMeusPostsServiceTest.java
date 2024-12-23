package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListarPostsResponse;
import br.com.cwi.crescer.api.controller.response.PostResponse;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ListarMeusPostsServiceTest {
    @InjectMocks
    private ListarMeusPostsService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("deve listar posts do usuario logado")
    public void deveListarPostsDoUsuarioLogado(){
        Usuario usuario = UsuarioFactory.get();
        Post post = PostFactory.get();
        post.setUsuario(usuario);
        post.setDataInclusao(LocalDateTime.now());
        usuario.getPosts().add(post);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        ListarPostsResponse postsResponse = tested.listar();

        PostResponse postResponse = postsResponse.getListaDePosts().get(0);
        assertEquals(postsResponse.getListaDePosts().size(), 1);
        assertEquals(postResponse.getId(), post.getId());
        assertEquals(postResponse.getAutor().getId(), usuario.getId());
        assertEquals(postResponse.getNroCurtidas(), 0 );
        assertEquals(postResponse.getNroComentarios(), 0);
        assertEquals(postResponse.getTexto(), post.getTexto());
        assertFalse(postResponse.isCurtido());

    }

    @Test
    @DisplayName("Deve retornar uma lista vazia caso nao tenham posts")
    public void deveRetornarListaVazia(){
        Usuario usuario = UsuarioFactory.get();
        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        ListarPostsResponse postsResponse = tested.listar();

        assertTrue(postsResponse.getListaDePosts().isEmpty());


    }
}
