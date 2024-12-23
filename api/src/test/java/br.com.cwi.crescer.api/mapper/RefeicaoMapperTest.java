package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.RefeicaoRequest;
import br.com.cwi.crescer.api.controller.response.RefeicaoResponse;
import br.com.cwi.crescer.api.domain.Refeicao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.factories.RefeicaoFactory.getBuilder;
import static br.com.cwi.crescer.api.factories.RefeicaoFactory.getRequest;
import static br.com.cwi.crescer.api.mapper.RefeicaoMapper.toEntity;
import static br.com.cwi.crescer.api.mapper.RefeicaoMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RefeicaoMapperTest {
    @Test
    @DisplayName("Deve retornar entity da refeição corretamente")
    void deveRetornarEntityRefeicao(){
        RefeicaoRequest request = getRequest();

        Refeicao entity = toEntity(request);

        assertEquals(request.getDia(), entity.getDia());
        assertEquals(request.getPeriodo(), entity.getPeriodo());
    }

    @Test
    @DisplayName("Deve retornar response da refeição corretamente")
    void deveRetornarResponseRefeicao(){
        Refeicao entity = getBuilder();

        RefeicaoResponse response = toResponse(entity);

        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getLipidios(), response.getLipidios());
        assertEquals(entity.getCalorias(), response.getCalorias());
        assertEquals(entity.getCarboidratos(), response.getCarboidratos());
        assertEquals(entity.getProteinas(), response.getProteinas());

    }
}
