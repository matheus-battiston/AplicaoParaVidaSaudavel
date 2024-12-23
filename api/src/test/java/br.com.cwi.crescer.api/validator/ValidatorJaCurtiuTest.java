package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.PostFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})

public class ValidatorJaCurtiuTest {

    @InjectMocks
    private ValidatorJaCurtiu tested;

    @Test
    @DisplayName("Deve retornar um erro se o usuario ja curtiu o post")
    public void deveRetornarErroSeJaCurtiu(){
        Post post = PostFactory.get();
        List<Post> listaDePosts = new ArrayList<>();
        listaDePosts.add(post);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.jaCurtiu(post, listaDePosts));

        assertEquals(ValidatorJaCurtiu.JA_CURTIU, exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar um erro se o usuario ainda nao curtiu o post")
    public void deveRetornarErroSeNaoCurtiu(){
        Post post = PostFactory.get();
        List<Post> listaDePosts = new ArrayList<>();

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.naoCurtiu(post, listaDePosts));

        assertEquals(ValidatorJaCurtiu.NAO_CURTIU, exception.getReason());
    }

    @Test
    @DisplayName("Não deve fazer nada se o usuário ainda não curtiu o post")
    public void naoDeveFazerNadaSeNaoCurtiuPost(){
        Post post = PostFactory.get();
        List<Post> listaDePosts = new ArrayList<>();

        assertDoesNotThrow(() -> tested.jaCurtiu(post, listaDePosts));
    }

    @Test
    @DisplayName("Não deve fazer nada se o usuário já curtiu o post")
    public void naoDeveFazerNadaSeJaCurtiuPost(){
        Post post = PostFactory.get();
        List<Post> listaDePosts = new ArrayList<>();
        listaDePosts.add(post);

        assertDoesNotThrow(() -> tested.naoCurtiu(post, listaDePosts));
    }


}
