package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.mapper.AlimentoMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AlimentoMapperTest {
    @Test
    @DisplayName("Deve retornar response do alimento corretamente")
    void deveRetornarResponseCorretamente(){
        Alimento alimento = AlimentoFactory.getBuilder();

        AlimentoResponse response = toResponse(alimento);

        assertEquals(alimento.getId(), response.getId());
        assertEquals(alimento.getNome(), response.getNome());
        assertEquals(alimento.getCalorias(), response.getCalorias());
        assertEquals(alimento.getProteinas(), response.getProteinas());
        assertEquals(alimento.getLipidios(), response.getLipidios());
        assertEquals(alimento.getCarboidratos(), response.getCarboidratos());
    }

}
