package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Periodo;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.factories.RefeicaoFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.NowService;
import br.com.cwi.crescer.api.service.ValidarPeriodoPossuiRefeicaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static br.com.cwi.crescer.api.domain.Periodo.LM;
import static br.com.cwi.crescer.api.factories.UsuarioFactory.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidarPeriodoPossuiRefeicaoServiceTest {
    @InjectMocks
    private ValidarPeriodoPossuiRefeicaoService tested;
    @Mock
    private NowService nowService;

    @Test
    @DisplayName("Deve retornar um erro se nao existe refeicao no periodo daquele dia")
    void deveTerErroSeNaoPossuiRefeicaoNoPeriodoNesseDia(){
        LocalDate hoje = LocalDate.now();
        Usuario usuario = get();
        Periodo periodo = Periodo.JA;
        Refeicao refeicao = RefeicaoFactory.getBuilder();
        refeicao.setPeriodo(periodo);
        refeicao.setDia(hoje.minusDays(10));
        usuario.getRefeicoes().add(refeicao);

        when(nowService.nowDate()).thenReturn(hoje);
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validar(usuario, periodo));

        assertEquals(ValidarPeriodoPossuiRefeicaoService.MENSAGEM_NAO_POSSUI_REFEICAO_NO_PERIODO, exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar um erro se nao existe refeicao")
    void deveTerErroSeNaoPossuiRefeicao(){
        Usuario usuario = get();
        Periodo periodo = Periodo.JA;

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validar(usuario, periodo));

        assertEquals(ValidarPeriodoPossuiRefeicaoService.MENSAGEM_NAO_POSSUI_REFEICAO_NO_PERIODO, exception.getReason());
    }

    @Test
    @DisplayName("Não deve fazer nada se o usuário possui uma refeição no período")
    void naoDeveFazerNadaSeUsuarioPossuiRefeicaoPeriodo(){
        Usuario usuario = get();
        Periodo periodo = LM;
        usuario.adicionarRefeicao(RefeicaoFactory.getBuilder());
        LocalDate hoje = LocalDate.now();

        when(nowService.nowDate()).thenReturn(hoje);

        assertDoesNotThrow(() -> tested.validar(usuario, periodo));
    }
}
