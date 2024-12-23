package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Periodo;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ValidarPeriodoPossuiRefeicaoService {
    public static final String MENSAGEM_NAO_POSSUI_REFEICAO_NO_PERIODO = "O período informado não possui uma refeição";

    @Autowired
    private NowService nowService;

    public void validar(Usuario usuario, Periodo periodo){

        boolean possuiRefeicaoPeriodo = usuario.getRefeicoes().stream()
                .anyMatch(refeicao -> refeicao.getPeriodo().equals(periodo) && refeicao.getDia().isEqual(nowService.nowDate()));

        if(!possuiRefeicaoPeriodo){
            throw new ResponseStatusException(BAD_REQUEST, MENSAGEM_NAO_POSSUI_REFEICAO_NO_PERIODO);
        }
    }
}
