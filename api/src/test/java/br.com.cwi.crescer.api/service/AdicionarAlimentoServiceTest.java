package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AlimentoRequest;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.AlimentoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.domain.TipoConquista.ALIMENTO;
import static br.com.cwi.crescer.api.factories.AlimentoFactory.getRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class AdicionarAlimentoServiceTest {
    @InjectMocks
    private AdicionarAlimentoService adicionarAlimentoService;

    @Mock
    private AlimentoRepository alimentoRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ConquistasService conquistasService;

    @Captor
    private ArgumentCaptor<Alimento> alimentoCaptor;

    @Test
    @DisplayName("Deve adicionar um alimento corretamente")
    void deveAdicionarAlimentoCorretamente(){
        AlimentoRequest request = getRequest();
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        adicionarAlimentoService.adicionar(request);

        verify(alimentoRepository).save(alimentoCaptor.capture());
        verify(conquistasService).conquistasQuantidadeDeAcoes(usuario, ALIMENTO);

        Alimento alimento = alimentoCaptor.getValue();

        assertTrue(alimento.isComunidade());
        assertEquals(request.getCalorias(), alimento.getCalorias());
        assertEquals(request.getLipidios(), alimento.getLipidios());
        assertEquals(request.getProteinas(), alimento.getProteinas());
        assertEquals(request.getCarboidratos(), alimento.getCarboidratos());

    }
}
