package br.com.cwi.crescer.api.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.validator.EditarRecomendacaoValidator.MENSAGEM_VALOR_INVALIDO;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
public class EditarRecomendacaoValidatorTest {
    @InjectMocks
    private EditarRecomendacaoValidator editarRecomendacaoValidator;

    @Test
    @DisplayName("Deve retornar ResponseStatusException se valor for menor ou igual a zero")
    void deveRetornarErroSeValorMenorOuIgualAZero(){
        Integer valor1 = -1;
        Integer valor2 = 0;

        ResponseStatusException exception1 =
                assertThrows(ResponseStatusException.class, () -> editarRecomendacaoValidator.validar(valor1));

        ResponseStatusException exception2 =
                assertThrows(ResponseStatusException.class, () -> editarRecomendacaoValidator.validar(valor2));


        assertEquals(MENSAGEM_VALOR_INVALIDO, exception1.getReason());
        assertEquals(MENSAGEM_VALOR_INVALIDO, exception2.getReason());
    }

    @Test
    @DisplayName("Não deve fazer nada se o valor for válido")
    void naoDeveFazerNadaSeValorValido(){
        int valor = 10;

        assertDoesNotThrow(() -> editarRecomendacaoValidator.validar(valor));
    }

}
