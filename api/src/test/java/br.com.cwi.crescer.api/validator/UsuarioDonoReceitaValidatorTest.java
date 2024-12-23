package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.factories.UsuarioFactory.get;
import static br.com.cwi.crescer.api.validator.UsuarioDonoReceitaValidator.MENSAGEM_USUARIO_NAO_E_DONO;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioDonoReceitaValidatorTest {

    @InjectMocks
    private UsuarioDonoReceitaValidator usuarioDonoReceitaValidator;

    @Test
    @DisplayName("Deve retornar ResponseStatusException se o usuário não for dono da receita")
    void deveRetornarErroSeUsuarioNaoEhDono(){
        Usuario usuarioDonoReceita = get();

        Usuario usuarioAutenticado = get();
        usuarioAutenticado.setNome("Autenticado");

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> usuarioDonoReceitaValidator.validar(usuarioDonoReceita, usuarioAutenticado));

        assertEquals(MENSAGEM_USUARIO_NAO_E_DONO, exception.getReason());
    }

    @Test
    @DisplayName("Não deve fazer nada se o usuário for dono")
    void naoDeveFazerNadaSeUsuarioForDono(){
        Usuario usuarioDonoReceita = get();

        assertDoesNotThrow(() -> usuarioDonoReceitaValidator.validar(usuarioDonoReceita, usuarioDonoReceita));
    }

}
