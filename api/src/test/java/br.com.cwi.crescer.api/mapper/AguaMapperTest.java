package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.AguaRequest;
import br.com.cwi.crescer.api.domain.Agua;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.factories.AguaFactory.getRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AguaMapperTest {
    @Test
    @DisplayName("Deve retornar entity da água corretamente")
    void deveRetornaEntityCorretamente(){
        AguaRequest request = getRequest();

        Agua entity = AguaMapper.toEntity(request);

        assertEquals(request.getQuantidade(), entity.getQuantidade());
    }
}
