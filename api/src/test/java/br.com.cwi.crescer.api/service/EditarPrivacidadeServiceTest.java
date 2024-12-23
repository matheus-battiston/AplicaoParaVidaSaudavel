package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.EditarPrivacidadeRequest;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.PostFactory;
import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class EditarPrivacidadeServiceTest {

    @InjectMocks
    private EditarPrivacidadeService tested;

    @Mock
    private BuscarPostService buscarPostService;

    @Mock
    private PostRepository postRepository;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    @Test
    @DisplayName("Deve editar privacidade de um post valido")
    public void deveEditarPrivacidade(){
        boolean novaPrivacidade = true;
        Post post = PostFactory.get();
        EditarPrivacidadeRequest request = EditarPrivacidadeRequest.builder()
                .novaPrivacidade(novaPrivacidade)
                .build();

        when(buscarPostService.porId(post.getId())).thenReturn(post);

        tested.editar(request, post.getId());

        verify(postRepository).save(postArgumentCaptor.capture());
        Post postCapturado = postArgumentCaptor.getValue();

        assertEquals(postCapturado.isPrivado(), novaPrivacidade);
    }

    @Test
    @DisplayName("Deve ter um erro quando nao existir o post")
    public void deveExistirErroSemOPostExistir(){
        boolean novaPrivacidade = false;
        Long idProcurado = SimpleFactory.getRandomLong();
        EditarPrivacidadeRequest request = EditarPrivacidadeRequest.builder()
                .novaPrivacidade(novaPrivacidade)
                .build();

        doThrow(ResponseStatusException.class).when(buscarPostService).porId(idProcurado);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.editar(request, idProcurado);
        });

        verify(postRepository, never()).save(any());

    }

}
