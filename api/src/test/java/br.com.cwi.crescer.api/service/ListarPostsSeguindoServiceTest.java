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

public class ListarPostsSeguindoServiceTest {
    @InjectMocks
    private ListarPostsSeguindoService tested;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("Deve listar proprios posts e posts de quem segue")
    public void deveRetornarPostsPropriosEDeQuemSegue(){
        Usuario usuario = UsuarioFactory.get();
        Usuario seguindo = UsuarioFactory.get();
        Post postUsuario = PostFactory.get();
        postUsuario.setDataInclusao(LocalDateTime.now());
        Post postSeguind = PostFactory.get();
        postSeguind.setDataInclusao(LocalDateTime.now());
        usuario.getPosts().add(postUsuario);
        seguindo.getPosts().add(postSeguind);
        usuario.getSeguindo().add(seguindo);
        Pageable pageable = PageRequest.of(0,5);

        List<Post> posts = List.of(postUsuario, postSeguind);
        Page<Post> postPaginados = new PageImpl<>(posts);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(postRepository.findTodosPostsSeguindo(usuario.getSeguindo(), usuario, pageable)).thenReturn(postPaginados);

        Page<PostResponse> response = tested.listar(pageable);

        verify(postRepository).findTodosPostsSeguindo(usuario.getSeguindo(), usuario, pageable);

        assertEquals(posts.size(), response.getSize());
        assertEquals(postUsuario.getId(), response.getContent().get(0).getId());
        assertEquals(postSeguind.getId(), response.getContent().get(1).getId());

    }

    @Test
    @DisplayName("Deve apresentar uma lista vazia quando nao segue ninguem nem post proprio")
    public void deveRetornarUmaListaVazia(){
        Usuario usuario = UsuarioFactory.get();

        Pageable pageable = PageRequest.of(0,5);

        Page<Post> postsPaginado = new PageImpl<>(new ArrayList<>());

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(postRepository.findTodosPostsSeguindo(usuario.getSeguindo(), usuario, pageable)).thenReturn(postsPaginado);

        Page<PostResponse> response = tested.listar(pageable);

        verify(postRepository).findTodosPostsSeguindo(usuario.getSeguindo(), usuario, pageable);

        assertTrue(response.getContent().isEmpty());

    }
}
