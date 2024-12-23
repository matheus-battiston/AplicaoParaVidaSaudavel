package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class CalcularAlimentoServiceTest {
    @InjectMocks
    private CalcularAlimentoService calcularAlimentoService;

    @Test
    @DisplayName("Deve retornar um alimento atualizado de acordo com quantidade da receita")
    void deveRetornarAlimentoAtualizado(){
        ReceitaAlimento receitaAlimento = new ReceitaAlimento();
        receitaAlimento.setAlimento(AlimentoFactory.getBuilder());
        receitaAlimento.getAlimento().setCalorias("100.0");
        receitaAlimento.getAlimento().setProteinas("100.0");
        receitaAlimento.getAlimento().setCarboidratos("100.0");
        receitaAlimento.getAlimento().setLipidios("100.0");
        receitaAlimento.setQuantidade("200");

        Alimento result = calcularAlimentoService.calcularNutrientes(receitaAlimento);

        assertEquals(result.getCalorias(), "200.0");
        assertEquals(result.getProteinas(), "200.0");
        assertEquals(result.getCarboidratos(), "200.0");
        assertEquals(result.getLipidios(), "200.0");
    }
}
