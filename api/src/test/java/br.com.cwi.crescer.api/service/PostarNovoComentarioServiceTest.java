package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.NovoComentarioRequest;
import br.com.cwi.crescer.api.domain.ComentarioPost;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.ComentarioFactory;
import br.com.cwi.crescer.api.factories.PostFactory;
import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ComentarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.domain.TipoConquista.COMENTAR_POST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class PostarNovoComentarioServiceTest {
    @InjectMocks
    private PostarNovoComentarioService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private BuscarPostService buscarPostService;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private ConquistasService conquistasService;

    @Captor
    private ArgumentCaptor<ComentarioPost> comentarioPostArgumentCaptor;


    @Test
    @DisplayName("Deve comentar um post")
    public void deveComentarPost(){
        Usuario usuario = UsuarioFactory.get();
        Post post = PostFactory.get();

        ComentarioPost novoComentario = ComentarioFactory.get();
        novoComentario.setPost(post);
        novoComentario.setUsuario(usuario);

        NovoComentarioRequest comentarioRequest = new NovoComentarioRequest();

        comentarioRequest.setTexto(novoComentario.getTexto());

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarPostService.porIdComAcesso(post.getId(), usuario)).thenReturn(post);

        tested.postar(comentarioRequest,post.getId());

        verify(comentarioRepository).save(comentarioPostArgumentCaptor.capture());
        verify(conquistasService).conquistasQuantidadeDeAcoes(usuario, COMENTAR_POST);
        ComentarioPost comentarioPost = comentarioPostArgumentCaptor.getValue();

        assertEquals(novoComentario.getTexto(), comentarioPost.getTexto());
        assertEquals(novoComentario.getPost(), comentarioPost.getPost());
        assertEquals(novoComentario.getUsuario(), comentarioPost.getUsuario());

    }

    @Test
    @DisplayName("Deve ter um erro quando usuario nao tem acesso ao post")
    public void deveTerErroQuandoNaoTemAcesso(){
        Usuario usuario = UsuarioFactory.get();
        Post post = PostFactory.get();
        post.setPrivado(true);

        NovoComentarioRequest comentarioRequest = new NovoComentarioRequest();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarPostService).porIdComAcesso(post.getId(), usuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.postar(comentarioRequest, post.getId());
        });

        verify(comentarioRepository, never()).save(any());

    }

    @Test
    @DisplayName("Deve ter um erro quando o post nao existe")
    public void deveTerErroQuandoPostNaoExiste(){
        Long postId = SimpleFactory.getRandomLong();
        Usuario usuario = UsuarioFactory.get();
        NovoComentarioRequest novoComentarioRequest = new NovoComentarioRequest();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarPostService).porIdComAcesso(postId, usuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.postar(novoComentarioRequest,postId);
        });

        verify(comentarioRepository, never()).save(any());


    }

}
