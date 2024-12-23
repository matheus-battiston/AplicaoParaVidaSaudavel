package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.repository.RefeicaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.service.BuscarRefeicaoService.MENSAGEM_REFEICAO_NAO_ENCONTRADA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BuscarRefeicaoServiceTest {
    @InjectMocks
    BuscarRefeicaoService buscarRefeicaoService;

    @Mock
    RefeicaoRepository refeicaoRepository;

    @Test
    @DisplayName("Deve retornar erro quando não encontrar refeição")
    void deveRetornarErro(){
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> buscarRefeicaoService.porId(1L));

        assertEquals(MENSAGEM_REFEICAO_NAO_ENCONTRADA, exception.getReason());
    }
}
