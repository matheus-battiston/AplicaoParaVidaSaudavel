package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.RemoverAlimentoRefeicaoRequest;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.repository.RefeicaoAlimentoRepository;
import br.com.cwi.crescer.api.repository.RefeicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RemoverAlimentoRefeicaoService {
    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Autowired
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    @Autowired
    private AtualizarRefeicaoService atualizarRefeicaoService;

    public void remover(Long idRefeicao, RemoverAlimentoRefeicaoRequest request){
        RefeicaoAlimento refAlimento = refeicaoAlimentoRepository.findByAlimento_IdAndRefeicao_Id(request.getIdAlimento(), idRefeicao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe um alimento com esse id nessa refeição"));
        Refeicao refeicao = refAlimento.getRefeicao();
        refeicao.removerAlimento(refAlimento);

        atualizarRefeicaoService.atualizar(refeicao);

        refeicaoAlimentoRepository.delete(refAlimento);

        refeicaoRepository.save(refeicao);
    }
}
