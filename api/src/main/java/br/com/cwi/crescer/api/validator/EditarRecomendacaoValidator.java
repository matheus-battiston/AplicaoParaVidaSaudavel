package br.com.cwi.crescer.api.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class EditarRecomendacaoValidator {
    public static final String MENSAGEM_VALOR_INVALIDO = "O valor informado deve ser maior do que 0";
    public void validar(Integer valor){
        if(valor <= 0){
            throw new ResponseStatusException(BAD_REQUEST, MENSAGEM_VALOR_INVALIDO);
        }
    }
}
