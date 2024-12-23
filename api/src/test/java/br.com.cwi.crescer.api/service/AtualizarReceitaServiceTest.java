package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class AtualizarReceitaServiceTest {
    @InjectMocks
    private AtualizarReceitaService atualizarReceitaService;

    @Test
    @DisplayName("Deve retornar receita atualizada de acordo com quantidade de alimentos")
    void deveRetornarReceitaAtualizado(){
        Receita receita = ReceitaFactory.get();
        receita.getAlimentos().add(new ReceitaAlimento(1L, receita, AlimentoFactory.getBuilder(), "200"));


        atualizarReceitaService.atualizar(receita);

        assertEquals(receita.getCalorias(), 200);
        assertEquals(receita.getProteinas(), 200);
        assertEquals(receita.getCarboidratos(), 200);
        assertEquals(receita.getLipidios(), 200);
    }
}
