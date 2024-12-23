package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.validator.ReceitaJaAvaliadaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetalharReceitaServiceTest {
    @InjectMocks
    private DetalharReceitaService detalharReceitaService;
    @Mock
    private BuscarReceitaService buscarReceitaService;
    @Mock
    private CalcularAlimentoService calcularAlimentoService;

    @Mock
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;


    @Test
    @DisplayName("Deve detalhar receita")
    void deveDetalharReceita() {
        Receita receita = ReceitaFactory.get();
        receita.adicionarAlimento(AlimentoFactory.getBuilder(), "100");
        receita.setCriador(UsuarioFactory.get());

        when(calcularAlimentoService.calcularNutrientes(receita.getAlimentos().get(0))).thenReturn(receita.getAlimentos().get(0).getAlimento());
        when(buscarReceitaService.porId(receita.getId())).thenReturn(receita);
        when(receitaJaAvaliadaValidator.validar(receita)).thenReturn(false);

        ReceitaResponse response = detalharReceitaService.detalhar(receita.getId());

        assertEquals(response.getId(),receita.getId());
        assertEquals(response.getDescricao(),receita.getDescricao());
        assertEquals(response.getDataCriacao(),receita.getDataCriacao());
        assertEquals(response.getUsuarioId(),receita.getUsuario().getId());
        assertEquals(response.getAlimentos().get(0).getId(),receita.getAlimentos().get(0).getAlimento().getId());
    }
}
