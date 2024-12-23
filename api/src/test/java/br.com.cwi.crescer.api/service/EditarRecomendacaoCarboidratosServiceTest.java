package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.EditarRecomendacaoRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.EditarRecomendacaoCarboidratosService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.EditarRecomendacaoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.factories.UsuarioFactory.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EditarRecomendacaoCarboidratosServiceTest {
    @InjectMocks
    private EditarRecomendacaoCarboidratosService editarRecomendacaoCarboidratosService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EditarRecomendacaoValidator editarRecomendacaoValidator;

    @Test
    @DisplayName("Deve editar a recomendação de carboidratos corretamente")
    void deveEditarRecomendacaoCarboidratos(){
        Usuario usuario = get();
        EditarRecomendacaoRequest request = new EditarRecomendacaoRequest();

        request.setValor(200);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        editarRecomendacaoCarboidratosService.editar(request);

        verify(usuarioRepository).save(usuario);
        verify(editarRecomendacaoValidator).validar(request.getValor());

        assertEquals(request.getValor(), usuario.getCarboidratosRecomendacao());
    }

}
