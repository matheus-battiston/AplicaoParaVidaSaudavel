package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.repository.RefeicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BuscarRefeicaoService {
    public static final String MENSAGEM_REFEICAO_NAO_ENCONTRADA = "Refeição não encontrada";
    @Autowired
    private RefeicaoRepository refeicaoRepository;

    public Refeicao porId(Long id) {
        return refeicaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, MENSAGEM_REFEICAO_NAO_ENCONTRADA));
    }
}
