package br.com.cwi.crescer.api.service;


import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.PostFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.PostRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.ValidatorUsuarioDonoDoPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemoverPostServiceTest {
    @InjectMocks
    private RemoverPostService tested;

    @Mock
    private BuscarPostService buscarPostService;
    @Mock
    private PostRepository postRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ValidatorUsuarioDonoDoPost validatorUsuarioDonoDoPost;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    @Test
    @DisplayName("Deve remover um post e as curtidas relacionadas")
    public void deveRemoverPost(){
        Usuario usuario = UsuarioFactory.get();
        Post post = PostFactory.get();
        post.setComentariosPost(new ArrayList<>());
        usuario.getPosts().add(post);
        post.setUsuario(usuario);
        usuario.adicionarCurtida(post);


        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarPostService.porId(post.getId())).thenReturn(post);

        tested.remover(post.getId());

        verify(validatorUsuarioDonoDoPost).dono(usuario.getPosts(), post);
        verify(postRepository).delete(postArgumentCaptor.capture());

        Post postCaptore = postArgumentCaptor.getValue();
        Usuario autor = postCaptore.getUsuario();

        assertEquals(0, autor.getPosts().size());
    }

    @Test
    @DisplayName("Deve retornar um erro quando tentar remover post que nao é autor")
    public void deveRetornarErroPorNaoSerDono(){
        Usuario usuario = UsuarioFactory.get();
        Post post = PostFactory.get();


        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarPostService.porId(post.getId())).thenReturn(post);
        doThrow(ResponseStatusException.class).when(validatorUsuarioDonoDoPost).dono(usuario.getPosts(), post);

        assertThrows(ResponseStatusException.class, () -> {
            tested.remover(post.getId());
        });

        verify(postRepository, never()).delete(any());

    }

    @Test
    @DisplayName("Deve retornar erro ao tentar remover post que nao existe")
    public void deveRetornarErroPorNaoExistir(){
        Usuario usuario = UsuarioFactory.get();
        Long postId = getRandomLong();


        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarPostService).porId(postId);

        assertThrows(ResponseStatusException.class, () -> {
            tested.remover(postId);
        });

        verify(postRepository, never()).delete(any());
    }
}
