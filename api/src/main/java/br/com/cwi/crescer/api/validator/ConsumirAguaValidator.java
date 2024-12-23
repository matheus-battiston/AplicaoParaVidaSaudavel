package br.com.cwi.crescer.api.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class ConsumirAguaValidator {
    public static final String MENSAGEM_QUANTIDADE_INVALIDA = "Água não deve ser negativa";
    public void validar(int quantidade, int aguaAtual){
        if((aguaAtual + quantidade) < 0){
            throw new ResponseStatusException(BAD_REQUEST, MENSAGEM_QUANTIDADE_INVALIDA);
        }
    }
}
