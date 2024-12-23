package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.PessoaRankingResponse;
import br.com.cwi.crescer.api.service.GerarRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private GerarRankingService gerarRankingService;

    @GetMapping
    public Page<PessoaRankingResponse> exibirRanking(Pageable pageable){
        return gerarRankingService.gerar(pageable);
    }
}
