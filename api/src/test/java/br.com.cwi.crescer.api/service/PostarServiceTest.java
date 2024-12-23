package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.NovoPostRequest;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.PostRepository;
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

import java.time.LocalDateTime;

import static br.com.cwi.crescer.api.domain.TipoConquista.POSTS;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostarServiceTest {
    @InjectMocks
    private PostarService tested;

    @Mock
    private PostRepository postRepository;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private NowService nowService;

    @Mock
    private ConquistasService conquistasService;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    @Test
    @DisplayName("Deve fazer um novo post, contabilizando os pontos")
    public void deveFazerUmNovoPost(){
        String pontosEsperados = "10";
        String textoPost = "Texto do post";
        String url = "URL";
        boolean privado = false;

        LocalDateTime agora = now();
        Usuario usuario = UsuarioFactory.get();

        NovoPostRequest request = NovoPostRequest.builder()
                .url(url)
                .texto(textoPost)
                .privado(privado)
                .build();


        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(nowService.now()).thenReturn(agora);

        tested.postar(request);

        verify(postRepository).save(postArgumentCaptor.capture());
        verify(conquistasService).conquistasQuantidadeDeAcoes(usuario, POSTS);

        Post postCapturado = postArgumentCaptor.getValue();
        Usuario usuarioDoPost = postCapturado.getUsuario();
        assertEquals(postCapturado.getTexto(), textoPost);
        assertEquals(postCapturado.getImagem(), url);
        assertEquals(postCapturado.isPrivado(), privado);
        assertEquals(postCapturado.getDataInclusao(), agora);
        assertEquals(usuarioDoPost.getPontuacao(), pontosEsperados);
        assertTrue(usuarioDoPost.getPosts().contains(postCapturado));
    }
}
