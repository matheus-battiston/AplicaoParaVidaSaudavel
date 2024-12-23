package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.validator.ValidatorJaTemPesoInicial;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({MockitoExtension.class})
public class ValidatorJaTemPesoInicialTest {
    @InjectMocks
    private ValidatorJaTemPesoInicial tested;
    @Test
    @DisplayName("Deve retornar um erro quando ja existe um peso")
    void deveRetornarErroQuandoExistePeso(){
        String peso = "100";
        Usuario usuario = UsuarioFactory.get();
        usuario.setPeso(peso);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.confirmaQueNaoTem(usuario));


        assertEquals(ValidatorJaTemPesoInicial.JA_TEM, exception.getReason());
    }
}
