package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.EditarRecomendacaoRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.EditarRecomendacaoAguaService;
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
public class EditarRecomendacaoAguaServiceTest {
    @InjectMocks
    private EditarRecomendacaoAguaService editarRecomendacaoAguaService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private EditarRecomendacaoValidator editarRecomendacaoValidator;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve editar a recomendação de água corretamente")
    void deveEditarRecomendacaoAgua(){
        Usuario usuario = get();
        EditarRecomendacaoRequest request = new EditarRecomendacaoRequest();

        request.setValor(200);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        editarRecomendacaoAguaService.editar(request);

        verify(usuarioRepository).save(usuario);
        verify(editarRecomendacaoValidator).validar(request.getValor());

        assertEquals(request.getValor(), usuario.getAguaRecomendacao());
    }
}
