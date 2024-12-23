package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.AvaliacaoReceitaRequest;
import br.com.cwi.crescer.api.controller.request.ReceitaPreferenciasRequest;
import br.com.cwi.crescer.api.controller.request.ReceitaRequest;
import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private DetalharReceitaService detalharReceitaService;
    @Autowired
    private CriarReceitaService criarReceitaService;

    @Autowired
    private AvaliacaoReceitaService avaliacaoReceitaService;

    @Autowired
    private ListarReceitasPessoaisService listarReceitasPessoaisService;
    @Autowired
    private ListarReceitasDoUsuarioService listarReceitasDoUsuarioService;

    @Autowired
    private ExibirReceitasSeguindoService exibirReceitasSeguindoService;

    @Autowired
    private CopiarReceitaService copiarReceitaService;

    @Autowired
    private ExibirPrincipaisReceitasService exibirPrincipaisReceitasService;

    @Autowired
    private RemoverReceitaService removerReceitaService;

    @Autowired
    private EditarReceitaService editarReceitaService;
    @Autowired
    private ListarSugestoesReceitaService listarSugestoesReceitaService;

    @GetMapping("/{id}")
    public ReceitaResponse detalharReceita(@PathVariable Long id) {
        return detalharReceitaService.detalhar(id);
    }

    @GetMapping("/{id}/sugestoes")
    public List<ReceitaResponse> listarSugestoes(@PathVariable Long id) {
        return listarSugestoesReceitaService.listar(id);
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public void criarReceita(@Valid @RequestBody ReceitaRequest request) {
        criarReceitaService.criar(request);
    }

    @PutMapping("/{id}/avaliar")
    @ResponseStatus(CREATED)
    public void avaliarReceita(@PathVariable Long id, @Valid @RequestBody AvaliacaoReceitaRequest request) {
        avaliacaoReceitaService.avaliar(id, request);
    }

    @GetMapping("/me")
    public Page<ReceitaResponse> receitasPessoais(Pageable pageable) {
        return listarReceitasPessoaisService.listar(pageable);
    }
    @GetMapping("/usuario/{userId}")
    public Page<ReceitaResponse> receitasDoUsuario(@PathVariable Long userId,Pageable pageable) {
        return listarReceitasDoUsuarioService.listar(userId,pageable);
    }

    @GetMapping("/seguindo")
    public Page<ReceitaResponse> exibirReceitasSeguindo(Pageable pageable) {
        return exibirReceitasSeguindoService.exibir(pageable);
    }

    @PutMapping("/{idReceita}/copia")
    @ResponseStatus(OK)
    public void copiarReceita(@PathVariable Long idReceita) {
        copiarReceitaService.copiar(idReceita);
    }

    @PostMapping("/top")
    public Page<ReceitaResponse> exibirTopReceitas(Pageable pageable, @RequestBody ReceitaPreferenciasRequest request){
        return exibirPrincipaisReceitasService.exibir(pageable, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void removerReceita(@PathVariable Long id) {
        removerReceitaService.remover(id);
    }

    @PutMapping("/{idReceita}")
    @ResponseStatus(OK)
    public void editarReceita(@PathVariable Long idReceita, @RequestBody @Valid ReceitaRequest request){
        editarReceitaService.editar(idReceita, request);
    }
}

