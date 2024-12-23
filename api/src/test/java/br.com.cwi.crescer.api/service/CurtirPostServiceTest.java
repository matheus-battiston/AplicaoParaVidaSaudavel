package br.com.cwi.crescer.api.service;


import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.PostFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.ValidatorJaCurtiu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.domain.TipoConquista.CURTIR_POST;
import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class CurtirPostServiceTest {

    @InjectMocks
    private CurtirPostService tested;

    @Mock
    private BuscarPostService buscarPostService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ValidatorJaCurtiu validatorJaCurtiu;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ConquistasService conquistasService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;


    @Test
    @DisplayName("Deve curtir um post")
    public void deveCurtirUmPost(){
        Post post = PostFactory.get();
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarPostService.porIdComAcesso(post.getId(), usuario)).thenReturn(post);


        tested.curtir(post.getId());

        verify(validatorJaCurtiu).jaCurtiu(post, usuario.getPostsCurtidos());
        verify(conquistasService).conquistasQuantidadeDeAcoes(usuario, CURTIR_POST);
        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());

        Usuario usuarioCaptor = usuarioArgumentCaptor.getValue();

        assertTrue(usuarioCaptor.getPostsCurtidos().contains(post));

    }

    @Test
    @DisplayName("Deve apresentar um erro quando usuario ja curtiu o post")
    public void deveTerErroQuandoJaCurtido(){
        Post post = PostFactory.get();
        Usuario usuario = UsuarioFactory.get();
        usuario.getPostsCurtidos().add(post);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarPostService.porIdComAcesso(post.getId(), usuario)).thenReturn(post);

        doThrow(ResponseStatusException.class).when(validatorJaCurtiu).jaCurtiu(post, usuario.getPostsCurtidos());

        assertThrows(ResponseStatusException.class, () -> {
            tested.curtir(post.getId());
        });

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar um erro quando o post nao existe")
    void deveTerErroQuandoPostNaoExiste(){
        Long postId = getRandomLong();
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarPostService).porIdComAcesso(postId, usuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.curtir(postId);
        });

        verify(usuarioRepository, never()).save(any());

    }

    @Test
    @DisplayName("Deve retornar um erro quando usuario na otem acesso ao post")
    void deveRetornarErroQuandoNaoTemAcesso(){
        Post post = PostFactory.get();
        post.setPrivado(true);
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarPostService).porIdComAcesso(post.getId(), usuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.curtir(post.getId());
        });

        verify(usuarioRepository, never()).save(any());
    }
}
