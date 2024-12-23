package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.controller.request.AguaRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.factories.AguaFactory.getRequest;
import static br.com.cwi.crescer.api.validator.ConsumirAguaValidator.MENSAGEM_QUANTIDADE_INVALIDA;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
public class ConsumirAguaValidatorTest {
    @InjectMocks
    private ConsumirAguaValidator consumirAguaValidator;

    @Test
    @DisplayName("Deve retornar ResponseStatusException se o valor inserido para consumo deixe a água do usuário negativa")
    void deveRetornarErroAoInserirValorInvalido(){
        AguaRequest request = getRequest();
        request.setQuantidade(-10);
        int somaTotalAguaUsuario = 0;

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> consumirAguaValidator.validar(request.getQuantidade(), somaTotalAguaUsuario));

        assertEquals(MENSAGEM_QUANTIDADE_INVALIDA, exception.getReason());
    }

    @Test
    @DisplayName("Não deve fazer nada se o valor inserido para consumo for válido")
    void naoDeveFazerNadaSeValorInseridoValido(){
        AguaRequest request = getRequest();
        request.setQuantidade(10);
        int somaTotalAguaUsuario = 0;

        assertDoesNotThrow(() -> consumirAguaValidator.validar(request.getQuantidade(), somaTotalAguaUsuario));
    }
}
