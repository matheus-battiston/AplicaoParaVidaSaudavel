package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static br.com.cwi.crescer.api.domain.TipoConquista.COPIA_RECEITAS;
import static br.com.cwi.crescer.api.factories.AlimentoFactory.getBuilder;
import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static br.com.cwi.crescer.api.factories.UsuarioFactory.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CopiarReceitaServiceTest {
    @InjectMocks
    private CopiarReceitaService tested;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private BuscarReceitaService buscarReceitaService;
    @Mock
    private ReceitaRepository receitaRepository;
    @Mock
    private BuscarAlimentoService buscarAlimentoService;

    @Mock
    private ConquistasService conquistasService;
    @Mock
    private NowService nowService;

    @Captor
    private ArgumentCaptor<Receita> receitaArgumentCaptor;

    @Test
    @DisplayName("Deve copiar uma receita")
    void deveCopiarReceita(){
        Usuario usuario = get();
        Receita receita = ReceitaFactory.get();
        Alimento alimento = getBuilder();
        receita.adicionarAlimento(alimento, "100");
        LocalDate hoje = LocalDate.now();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarReceitaService.porId(receita.getId())).thenReturn(receita);
        when(buscarAlimentoService.porId(alimento.getId())).thenReturn(alimento);
        when(nowService.nowDate()).thenReturn(hoje);

        tested.copiar(receita.getId());

        verify(receitaRepository).save(receitaArgumentCaptor.capture());
        verify(conquistasService).conquistasQuantidadeDeAcoes(receita.getUsuario(), COPIA_RECEITAS);
        Receita receitaCapture = receitaArgumentCaptor.getValue();

        assertEquals( usuario.getId(), receitaCapture.getUsuario().getId());
        assertEquals(alimento, receitaCapture.getAlimentos().get(0).getAlimento());
        assertEquals(1, receitaCapture.getAlimentos().size());
        assertTrue(receitaCapture.isCopia());
        assertTrue(receitaCapture.isPrivado());
        assertEquals(1, receitaCapture.getUsuario().getReceitas().size());
    }

    @Test
    @DisplayName("Deve ter erro quando receita nao existe")
    void deveTerErroQuandoReceitaNaoExiste(){
        Usuario usuario = get();
        Long idReceita = getRandomLong();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarReceitaService).porId(idReceita);

        assertThrows(ResponseStatusException.class, () -> {
            tested.copiar(idReceita);
        });

        verify(receitaRepository, never()).save(any());
    }
}
