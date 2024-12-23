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

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class RemoverCurtidaServiceTest {
    @InjectMocks
    private RemoverCurtidaService tested;

    @Mock
    private BuscarPostService buscarPostService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ValidatorJaCurtiu validatorJaCurtiu;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;


    @Test
    @DisplayName("Deve remover a curtida de um post")
    public void remover(){
        Post post = PostFactory.get();
        Usuario usuario = UsuarioFactory.get();
        usuario.getPostsCurtidos().add(post);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarPostService.porIdComAcesso(post.getId(), usuario)).thenReturn(post);

        tested.remover(post.getId());

        verify(validatorJaCurtiu).naoCurtiu(post, usuario.getPostsCurtidos());
        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());

        Usuario usuarioCaptore = usuarioArgumentCaptor.getValue();

        assertFalse(usuarioCaptore.getPostsCurtidos().contains(post));
    }

    @Test
    @DisplayName("Deve ter um erro se post nao existir")
    public void deveTerErroSePostNaoExistir(){
        Usuario usuario = UsuarioFactory.get();
        Long postId = getRandomLong();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarPostService).porIdComAcesso(postId, usuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.remover(postId);
        });

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve apresentar um erro quando usuario nao curtiu o post ainda")
    public void deveTerErroQuandoJaCurtido(){
        Post post = PostFactory.get();
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarPostService.porIdComAcesso(post.getId(), usuario)).thenReturn(post);
        doThrow(ResponseStatusException.class).when(validatorJaCurtiu).naoCurtiu(post, usuario.getPostsCurtidos());

        assertThrows(ResponseStatusException.class, () -> {
            tested.remover(post.getId());
        });

        verify(validatorJaCurtiu).naoCurtiu(post, usuario.getPostsCurtidos());
        verify(usuarioRepository, never()).save(any());
    }
}
