package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.DefinirGeneroRequest;
import br.com.cwi.crescer.api.security.domain.Sexo;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.DefinirGeneroService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.security.domain.Sexo.F;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefinirGeneroServiceTest {

    @InjectMocks
    private DefinirGeneroService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @DisplayName("Deve definir um genero para o usuario")
    void definirGenero(){
        Sexo sexo = F;
        Usuario usuario = UsuarioFactory.get();
        DefinirGeneroRequest request = new DefinirGeneroRequest();
        request.setSexo(sexo);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.definir(request);

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        verify(calcularRecomendacoesUsuarioService).calcular();
        Usuario capturado = usuarioArgumentCaptor.getValue();

        assertEquals(sexo, capturado.getSexo());
    }
}
