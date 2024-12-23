package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Periodo;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.factories.RefeicaoFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static br.com.cwi.crescer.api.domain.Periodo.LM;
import static br.com.cwi.crescer.api.factories.RefeicaoFactory.getBuilder;
import static br.com.cwi.crescer.api.service.ValidarPeriodoPossuiRefeicaoService.MENSAGEM_NAO_POSSUI_REFEICAO_NO_PERIODO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidarPeriodoPossuiRefeicaoServiceTest {
    @InjectMocks
    private ValidarPeriodoPossuiRefeicaoService validarPeriodoPossuiRefeicaoService;

    @Mock
    private NowService nowService;

    @Test
    @DisplayName("Deve retornar ResponseStatusException se não possuir refeição no período")
    void deveRetornarErroSeNaoPossuiRefeicaoPeriodo(){
        Usuario usuario = UsuarioFactory.get();
        Refeicao refeicao = getBuilder();

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> validarPeriodoPossuiRefeicaoService.validar(usuario, refeicao.getPeriodo()));

        assertEquals(MENSAGEM_NAO_POSSUI_REFEICAO_NO_PERIODO, exception.getReason());
    }

    @Test
    @DisplayName("Não deve fazer nada se possuir refeição no período")
    void naoDeveFazerNadaSePossuirRefeicaoPeriodo(){
        Usuario usuario = UsuarioFactory.get();
        Periodo periodo = LM;

        Refeicao refeicao = RefeicaoFactory.getBuilder();
        refeicao.setPeriodo(periodo);
        usuario.adicionarRefeicao(refeicao);

        when(nowService.nowDate()).thenReturn(LocalDate.now());

        assertDoesNotThrow(() -> validarPeriodoPossuiRefeicaoService.validar(usuario, periodo));
    }

}
