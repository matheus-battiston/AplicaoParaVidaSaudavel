package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.PostFactory;
import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.PostRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static br.com.cwi.crescer.api.service.BuscarPostService.NAO_AUTORIZADO;
import static br.com.cwi.crescer.api.service.BuscarPostService.POST_NAO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class BuscarPostServiceTest {

    @InjectMocks
    private BuscarPostService tested;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("Deve retornar o post correto quando pesquisado")
    public void deveRetornarPostCerto(){
        Post post = PostFactory.get();

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        Post retorno = tested.porId(post.getId());

        verify(postRepository).findById(post.getId());
        assertEquals(post, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando nao encontrar post")
    void deveRetornarErro(){
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals(POST_NAO_ENCONTRADO, exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar erro quando nao encontrar post ou nao tiver acesso")
    void deveRetornarErroPorNaoAcharOuNaoTerAcesso(){
        Usuario usuario = UsuarioFactory.get();
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porIdComAcesso(SimpleFactory.getRandomLong(), usuario));


        assertEquals(NAO_AUTORIZADO, exception.getReason());
    }


    @Test
    @DisplayName("Deve retornar o post correto quando pesquisado e tiver acesso")
    public void deveRetornarPostCertoComAcesso(){
        Post post = PostFactory.get();
        Usuario usuario = UsuarioFactory.get();

        when(postRepository.findAllowedById(post.getId(), usuario)).thenReturn(Optional.of(post));

        Post retorno = tested.porIdComAcesso(post.getId(), usuario);

        verify(postRepository).findAllowedById(post.getId(), usuario);
        assertEquals(post, retorno);
    }
}
