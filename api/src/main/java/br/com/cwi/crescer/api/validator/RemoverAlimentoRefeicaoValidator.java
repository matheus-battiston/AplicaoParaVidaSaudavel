package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Refeicao;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class RemoverAlimentoRefeicaoValidator {
    public static final String MENSAGEM_REFEICAO_NAO_POSSUI_ALIMENTO = "O alimento informado não possui a esta refeição";
    public void validar(Refeicao refeicao, Alimento alimento){
        boolean refeicaoPossuiAlimento = refeicao.getAlimentos().stream()
                .anyMatch(alimento1 -> alimento1.getAlimento().equals(alimento));

        if(!refeicaoPossuiAlimento){
            throw new ResponseStatusException(BAD_REQUEST, MENSAGEM_REFEICAO_NAO_POSSUI_ALIMENTO);
        }
    }
}
