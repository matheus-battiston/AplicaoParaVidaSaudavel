package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.EditarRecomendacaoRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.EditarRecomendacaoProteinasService;
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
public class EditarRecomendacaoProteinasServiceTest {
    @InjectMocks
    private EditarRecomendacaoProteinasService editarRecomendacaoProteinasService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private EditarRecomendacaoValidator editarRecomendacaoValidator;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve editar a recomendação de proteinas corretamente")
    void deveEditarRecomendacaoProteinas(){
        Usuario usuario = get();
        EditarRecomendacaoRequest request = new EditarRecomendacaoRequest();

        request.setValor(200);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        editarRecomendacaoProteinasService.editar(request);

        verify(usuarioRepository).save(usuario);
        verify(editarRecomendacaoValidator).validar(request.getValor());

        assertEquals(request.getValor(), usuario.getProteinasRecomendacao());
    }
}
