package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.repository.AlimentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class BuscarAlimentoServiceTest {

    @InjectMocks
    private BuscarAlimentoService tested;

    @Mock
    private AlimentoRepository alimentoRepository;


    @Test
    @DisplayName("Deve retornar o alimento correto quando pesquisado")
    public void deveRetornarAlimentoCerto(){
        Alimento alimento = AlimentoFactory.getBuilder();

        when(alimentoRepository.findById(alimento.getId())).thenReturn(Optional.of(alimento));

        Alimento retorno = tested.porId(alimento.getId());

        verify(alimentoRepository).findById(alimento.getId());
        assertEquals(alimento, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando nao encontrar receita")
    void deveRetornarErro(){
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Alimento não encontrado", exception.getReason());
    }
}
