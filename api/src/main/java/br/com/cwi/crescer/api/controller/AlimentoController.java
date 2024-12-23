package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.AlimentoRequest;
import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.service.AdicionarAlimentoService;
import br.com.cwi.crescer.api.service.ListarAlimentosRecentementeUsadosService;
import br.com.cwi.crescer.api.service.ListarAlimentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    @Autowired
    private ListarAlimentosService listarAlimentosService;

    @Autowired
    private AdicionarAlimentoService adicionarAlimentoService;

    @Autowired
    private ListarAlimentosRecentementeUsadosService listarAlimentosRecentementeUsadosService;


    @GetMapping()
    public Page<AlimentoResponse> listarAlimentosPaginados(@RequestParam("text") Optional<String> nome, Pageable pageable) {
        String paramValue = nome.orElse("");
        return listarAlimentosService.listarPaginado(paramValue, pageable);
    }

    @PostMapping()
    public void adicionarAlimento(@RequestBody @Valid AlimentoRequest request){
        adicionarAlimentoService.adicionar(request);
    }

    @GetMapping("/recentes")
    public List<AlimentoResponse> listarAlimentosRecentementeUsados(){
        return listarAlimentosRecentementeUsadosService.listar();
    }

}
