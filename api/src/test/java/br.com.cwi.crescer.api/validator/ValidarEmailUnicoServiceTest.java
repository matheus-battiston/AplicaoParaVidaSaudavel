package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.ValidarEmailUnicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ValidarEmailUnicoServiceTest {
    @InjectMocks
    private ValidarEmailUnicoService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar erro se ja existe email")
    void deveRetornarErroPorJaExistirEmail(){
        Usuario usuarioUm = UsuarioFactory.get();
        List<Usuario> usuarios = List.of(usuarioUm);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validar(usuarioUm.getEmail()));

        assertEquals(ValidarEmailUnicoService.EMAIL_JA_EXISTE, exception.getReason());

    }

}
