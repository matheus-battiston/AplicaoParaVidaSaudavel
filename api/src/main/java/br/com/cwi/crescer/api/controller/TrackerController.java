package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.AguaRequest;
import br.com.cwi.crescer.api.controller.request.RefeicaoPorReceitaRequest;
import br.com.cwi.crescer.api.controller.request.RefeicaoRequest;
import br.com.cwi.crescer.api.controller.request.RemoverAlimentoRefeicaoRequest;
import br.com.cwi.crescer.api.controller.response.RefeicaoResponse;
import br.com.cwi.crescer.api.controller.response.RelatorioResponse;
import br.com.cwi.crescer.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tracker")
public class TrackerController {

    @Autowired
    private ConsumirAguaService consumirAguaService;



    @Autowired
    private AdicionarAlimentoRefeicaoService adicionarAlimentoRefeicaoService;

    @Autowired
    private AdicionarAlimentoRefeicaoPorReceitaService adicionarAlimentoRefeicaoPorReceitaService;

    @Autowired
    private ListarRefeicoesService listarRefeicoesService;

    @Autowired
    private RemoverAlimentoRefeicaoService removerAlimentoRefeicaoService;

    @Autowired
    private GerarRelatorioService gerarRelatorioService;

    @PutMapping("/agua")
    public void consumirAgua(@RequestBody @Valid AguaRequest request){
        consumirAguaService.consumir(request);
    }


    @PostMapping("/refeicao")
    public RefeicaoResponse adicionarAlimentoRefeicao(@RequestBody @Valid RefeicaoRequest request){
        return adicionarAlimentoRefeicaoService.adicionar(request);
    }

    @PostMapping("/refeicao/receita")
    public RefeicaoResponse adicionarAlimentoRefeicaoPorReceita(@RequestBody @Valid RefeicaoPorReceitaRequest request){
        return adicionarAlimentoRefeicaoPorReceitaService.adicionar(request);
    }

    @GetMapping("/refeicao/listar/{dia}")
    public List<RefeicaoResponse> listarRefeicoes(@PathVariable String dia){
        return listarRefeicoesService.listar(LocalDate.parse(dia));
    }

    @PostMapping("/refeicao/remover/{idRefeicao}")
    public void removerAlimentoRefeicao(@PathVariable Long idRefeicao, @RequestBody @Valid RemoverAlimentoRefeicaoRequest request){
        removerAlimentoRefeicaoService.remover(idRefeicao, request);
    }

    @GetMapping("/relatorio/{dia}")
    public RelatorioResponse gerarRelatorio(@PathVariable String dia){
        return gerarRelatorioService.gerar(LocalDate.parse(dia));
    }
}
