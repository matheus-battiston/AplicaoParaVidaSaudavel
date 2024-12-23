package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.validator.ValidatorJaSegue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.security.validator.ValidatorJaSegue.JA_SEGUE;
import static br.com.cwi.crescer.api.security.validator.ValidatorJaSegue.NAO_SEGUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({MockitoExtension.class})
public class ValidatorJaSegueTest {

    @InjectMocks
    private ValidatorJaSegue tested;

    @Test
    @DisplayName("Deve retornar um erro quando usuario ja segue o outro")
    void deveRetornarErroQuandoJaSegue(){
        Usuario usuarioSeguidor = UsuarioFactory.get();
        Usuario usuarioSeguido = UsuarioFactory.get();
        usuarioSeguidor.getSeguindo().add(usuarioSeguido);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validarNaoSegue(usuarioSeguidor, usuarioSeguido));

        assertEquals(JA_SEGUE, exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar um erro quando usuario nao segue o outro")
    void deveRetornarErroQuandoNaoSegue(){
        Usuario usuarioSeguidor = UsuarioFactory.get();
        Usuario usuarioSeguido = UsuarioFactory.get();

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validarJaSegue(usuarioSeguidor, usuarioSeguido));

        assertEquals(NAO_SEGUE, exception.getReason());
    }
}
