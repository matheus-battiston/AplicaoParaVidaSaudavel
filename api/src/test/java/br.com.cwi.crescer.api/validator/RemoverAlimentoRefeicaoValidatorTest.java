package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.RefeicaoFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.validator.RemoverAlimentoRefeicaoValidator.MENSAGEM_REFEICAO_NAO_POSSUI_ALIMENTO;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RemoverAlimentoRefeicaoValidatorTest {
    @InjectMocks
    private RemoverAlimentoRefeicaoValidator removerAlimentoRefeicaoValidator;

    @Test
    @DisplayName("Deve retornar ResponseStatusException se refeição não possuir alimento")
    void deveRetornarErroSeRefeicaoNaoPossuiAlimento(){
        Refeicao refeicao = RefeicaoFactory.getBuilder();
        Alimento alimento = AlimentoFactory.getBuilder();

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> removerAlimentoRefeicaoValidator.validar(refeicao, alimento));

        assertEquals(MENSAGEM_REFEICAO_NAO_POSSUI_ALIMENTO, exception.getReason());
    }

    @Test
    @DisplayName("Não deve fazer nada se a refeição possui alimento")
    void naoDeveFazerNadaSeRefeicaoPossuiAlimento(){
        Refeicao refeicao = RefeicaoFactory.getBuilder();
        Alimento alimento = AlimentoFactory.getBuilder();

        RefeicaoAlimento refeicaoAlimento = new RefeicaoAlimento();
        refeicaoAlimento.setRefeicao(refeicao);
        refeicaoAlimento.setAlimento(alimento);
        refeicaoAlimento.setQuantidade("100");

        refeicao.adicionarAlimento(refeicaoAlimento);

        assertDoesNotThrow(() -> removerAlimentoRefeicaoValidator.validar(refeicao, alimento));
    }
}

