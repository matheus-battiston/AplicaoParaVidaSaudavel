package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.PostFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})

public class ValidatorUsuarioDonoDoPostTest {
    @InjectMocks
    private ValidatorUsuarioDonoDoPost tested;

    @Test
    @DisplayName("Deve retornar um erro se o usuario nao é dono do post")
    public void deveRetornarErroSeNaoForDono(){
        Usuario usuario = UsuarioFactory.get();
        Post post = PostFactory.get();

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.dono(usuario.getPosts(), post));

        assertEquals(ValidatorUsuarioDonoDoPost.NAO_EH_DONO, exception.getReason());
    }

    @Test
    @DisplayName("Não deve fazer nada se o usuário é dono do post")
    public void naoDeveFazerNadaSeUsuarioDonoPost(){
        Usuario usuario = UsuarioFactory.get();
        Post post = PostFactory.get();
        usuario.adicionarPost(post);
        post.setUsuario(usuario);

        assertDoesNotThrow(() -> tested.dono(usuario.getPosts(), post));
    }
}
