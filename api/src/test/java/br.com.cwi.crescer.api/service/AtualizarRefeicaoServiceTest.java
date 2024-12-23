package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.RefeicaoFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class AtualizarRefeicaoServiceTest {
    @InjectMocks
    private AtualizarRefeicaoService tested;

    @Test
    @DisplayName("Deve atualizar as infos de uma refeicao para 0 caso nao tenha nada mais")
    void deveAtualizarInfosDaRefeicao(){
        Refeicao refeicao = RefeicaoFactory.getBuilder();

        tested.atualizar(refeicao);

        assertEquals(0, refeicao.getCalorias());
        assertEquals(0, refeicao.getLipidios());
        assertEquals(0, refeicao.getProteinas());
        assertEquals(0, refeicao.getCarboidratos());
    }

    @Test
    @DisplayName("Deve atualizar as infos de uma refeicao somando as infos dos alimentos")
    void deveSomarInfosDosAlimentos(){
        Refeicao refeicao = RefeicaoFactory.getBuilder();
        Alimento alimento = AlimentoFactory.getBuilder();
        RefeicaoAlimento refeicaoAlimento = new RefeicaoAlimento();
        refeicaoAlimento.setAlimento(alimento);
        refeicaoAlimento.setQuantidade("100");

        RefeicaoAlimento outroAlimento = new RefeicaoAlimento();
        outroAlimento.setAlimento(alimento);
        outroAlimento.setQuantidade("100");

        refeicao.getAlimentos().add(refeicaoAlimento);
        refeicao.getAlimentos().add(outroAlimento);

        tested.atualizar(refeicao);

        assertEquals(200, refeicao.getCalorias());
        assertEquals(200, refeicao.getLipidios());
        assertEquals(200, refeicao.getProteinas());
        assertEquals(200, refeicao.getCarboidratos());
    }
}
