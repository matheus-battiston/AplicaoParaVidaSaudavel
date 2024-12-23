package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AvaliacaoReceitaRequest;
import br.com.cwi.crescer.api.domain.AvaliacaoReceita;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.AvaliacaoReceitaRepository;
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

import static br.com.cwi.crescer.api.domain.TipoConquista.AVALIAR_RECEITAS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})

public class AvaliacaoReceitaServiceTest {
    @InjectMocks
    private AvaliacaoReceitaService tested;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private BuscarReceitaService buscarReceitaService;
    @Mock
    private AvaliacaoReceitaRepository avaliacaoReceitaRepository;

    @Mock
    private ConquistasService conquistasService;

    @Captor
    private ArgumentCaptor<AvaliacaoReceita> avaliacaoReceitaArgumentCaptor;

    @Test
    @DisplayName("Deve realizar uma avaliacao e atualizar a nota geral")
    void deveRealizarUmaAvaliacao(){
        Receita receita = ReceitaFactory.get();
        Usuario autenticado = UsuarioFactory.get();
        AvaliacaoReceitaRequest request = new AvaliacaoReceitaRequest();
        request.setNota("4");
        when(usuarioAutenticadoService.get()).thenReturn(autenticado);
        when(buscarReceitaService.porId(receita.getId())).thenReturn(receita);

        tested.avaliar(receita.getId(), request);

        verify(avaliacaoReceitaRepository).save(avaliacaoReceitaArgumentCaptor.capture());
        verify(conquistasService).conquistasQuantidadeDeAcoes(autenticado, AVALIAR_RECEITAS);

        AvaliacaoReceita avaliacaoReceita = avaliacaoReceitaArgumentCaptor.getValue();

        assertEquals(avaliacaoReceita.getReceita().getAvaliacoes().size(), 1);
        assertEquals(avaliacaoReceita.getUsuario().getId(), autenticado.getId());
        assertEquals( "4.0",avaliacaoReceita.getReceita().getNota());
    }

    @Test
    @DisplayName("Deve apresentar um erro quando usuario nao tem permissao para receita")
    void erroQuandoNaoTemPermissao(){
        Receita receita = ReceitaFactory.get();
        receita.setPrivado(true);

        Usuario autenticado = UsuarioFactory.get();
        AvaliacaoReceitaRequest request = new AvaliacaoReceitaRequest();
        request.setNota("5");

        when(usuarioAutenticadoService.get()).thenReturn(autenticado);
        doThrow(ResponseStatusException.class).when(buscarReceitaService).porId(receita.getId());

        assertThrows(ResponseStatusException.class, () -> {
            tested.avaliar(receita.getId(), request);
        });



        verify(avaliacaoReceitaRepository, never()).save(any());

    }
}
