package br.com.cwi.crescer.api.service;


import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.DefinirAtivdadeRequest;
import br.com.cwi.crescer.api.security.domain.AtividadeFisica;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.DefinirAtividadeFisicaService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.security.domain.AtividadeFisica.I;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefinirAtividadeFisicaServiceTest {
    @InjectMocks
    private DefinirAtividadeFisicaService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @DisplayName("deve definir um comportamento de atividade fisica")
    void deveDefinirComportamentoDeAtividadeFisica(){
        Usuario usuario = UsuarioFactory.get();
        AtividadeFisica atividadeFisica = I;
        DefinirAtivdadeRequest request = new DefinirAtivdadeRequest();
        request.setAtividadeFisica(atividadeFisica);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.definir(request);

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        verify(calcularRecomendacoesUsuarioService).calcular();
        Usuario usuarioCapturado = usuarioArgumentCaptor.getValue();

        assertEquals(atividadeFisica, usuarioCapturado.getAtividadeFisica());
    }
}
