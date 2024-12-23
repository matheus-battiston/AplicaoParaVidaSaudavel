package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.DefinirMetaRequest;
import br.com.cwi.crescer.api.security.domain.Meta;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.DefinirMetaService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefinirMetaServiceTest {
    @InjectMocks
    private DefinirMetaService tested;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;


    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @DisplayName("deve definir uma meta")
    void deveDefinirMeta(){
        Usuario usuario = UsuarioFactory.get();
        Meta meta = Meta.M;
        DefinirMetaRequest request = new DefinirMetaRequest();
        request.setMeta(meta);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.definirMeta(request);

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        verify(calcularRecomendacoesUsuarioService).calcular();
        Usuario usuarioCapturado = usuarioArgumentCaptor.getValue();

        assertEquals(meta, usuarioCapturado.getMeta());

    }
}
