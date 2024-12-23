package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.PostResponse;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.PostFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.PostRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})

public class ListarPostDeUmUsuarioServiceTest {
    @InjectMocks
    private ListarPostDeUmUsuarioService tested;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("Deve listar os posts de um usuario e paginado")
    public void deveListarPostsDeUmUsuario(){
        Usuario usuario = UsuarioFactory.get();
        Usuario visitado = UsuarioFactory.get();

        Post postUm = PostFactory.get();
        postUm.setDataInclusao(LocalDateTime.now());
        Post postDois = PostFactory.get();
        postDois.setDataInclusao(LocalDateTime.now());
        visitado.adicionarPost(postUm);
        visitado.adicionarPost(postDois);
        Pageable pageable = PageRequest.of(0,5);
        List<Post> posts = List.of(postUm, postDois);
        Page<Post> postsPaginados = new PageImpl<>(posts);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(postRepository.findPostsDoUsuarioPermitido(visitado.getId(), usuario, pageable)).thenReturn(postsPaginados);

        Page<PostResponse> responses = tested.listar(visitado.getId(), pageable);

        verify(postRepository).findPostsDoUsuarioPermitido(visitado.getId(), usuario, pageable);

        assertEquals(posts.size(), responses.getSize());
        assertEquals(postUm.getId(), responses.getContent().get(0).getId());
        assertEquals(postDois.getId(), responses.getContent().get(1).getId());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nao tem posts")
    public void deveRetornarListaVaziaQuandoNaoTemPosts(){
        Usuario usuario = UsuarioFactory.get();
        Usuario visitado = UsuarioFactory.get();
        Page<Post> postsPaginado = new PageImpl<>(new ArrayList<>());
        Pageable pageable = PageRequest.of(0,5);


        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(postRepository.findPostsDoUsuarioPermitido(visitado.getId(), usuario, pageable)).thenReturn(postsPaginado);

        Page<PostResponse> responses = tested.listar(visitado.getId(), pageable);

        verify(postRepository).findPostsDoUsuarioPermitido(visitado.getId(), usuario, pageable);
        assertTrue(responses.getContent().isEmpty());

    }
}
