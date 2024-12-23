package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.RemoverAlimentoRefeicaoRequest;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.repository.RefeicaoAlimentoRepository;
import br.com.cwi.crescer.api.repository.RefeicaoRepository;
import br.com.cwi.crescer.api.validator.RemoverAlimentoRefeicaoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static br.com.cwi.crescer.api.factories.RefeicaoFactory.getBuilder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemoverAlimentoRefeicaoServiceTest {
    @InjectMocks
    private RemoverAlimentoRefeicaoService removerAlimentoRefeicaoService;

    @Mock
    private BuscarRefeicaoService buscarRefeicaoService;

    @Mock
    private BuscarAlimentoService buscarAlimentoService;

    @Mock
    private RefeicaoRepository refeicaoRepository;

    @Mock
    private RemoverAlimentoRefeicaoValidator removerAlimentoRefeicaoValidator;

    @Mock
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    @Mock
    private AtualizarRefeicaoService atualizarRefeicaoService;

    @Captor
    private ArgumentCaptor<Refeicao> refeicaoArgumentCaptor;

    @Test
    @DisplayName("Deve remover um alimento de uma refeição corretamente")
    void deveRemoverAlimento(){
        Refeicao refeicao = getBuilder();
        Alimento alimento = AlimentoFactory.getBuilder();
        RefeicaoAlimento refeicaoAlimento = new RefeicaoAlimento();

        refeicaoAlimento.setAlimento(alimento);
        refeicaoAlimento.setRefeicao(refeicao);
        refeicaoAlimento.setQuantidade("100");

        refeicao.adicionarAlimento(refeicaoAlimento);

        List<RefeicaoAlimento> alimentosAntigo = refeicao.getAlimentos();

        RemoverAlimentoRefeicaoRequest request = new RemoverAlimentoRefeicaoRequest();
        request.setIdAlimento(alimento.getId());

        RefeicaoAlimento refeicaoAlimentoADeletar = new RefeicaoAlimento();
        refeicaoAlimentoADeletar.setRefeicao(refeicao);
        refeicaoAlimentoADeletar.setAlimento(alimento);
        refeicaoAlimentoADeletar.setQuantidade(refeicao.getAlimentos().get(0).getQuantidade());

        when(refeicaoAlimentoRepository.findByAlimento_IdAndRefeicao_Id(alimento.getId(), refeicao.getId())).thenReturn(Optional.of(refeicaoAlimentoADeletar));

        removerAlimentoRefeicaoService.remover(refeicao.getId(), request);

        verify(atualizarRefeicaoService).atualizar(refeicao);
        verify(refeicaoAlimentoRepository).delete(refeicaoAlimentoADeletar);
        verify(refeicaoRepository).save(refeicaoArgumentCaptor.capture());

        assertTrue(refeicaoArgumentCaptor.getValue().getAlimentos().isEmpty());


    }

    @Test
    @DisplayName("Não deve remover um alimento de uma refeição se esta não possuir o alimento indicado")
    void naoDeveRemoverAlimentoRefeicaoSeAlimentoInvalido(){
        Refeicao refeicao = getBuilder();
        Alimento alimento = AlimentoFactory.getBuilder();

        RemoverAlimentoRefeicaoRequest request = new RemoverAlimentoRefeicaoRequest();
        request.setIdAlimento(alimento.getId());

        when(refeicaoAlimentoRepository.findByAlimento_IdAndRefeicao_Id(alimento.getId(), refeicao.getId())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> removerAlimentoRefeicaoService.remover(refeicao.getId(), request));

        verify(refeicaoRepository, never()).save(any());
    }

}
