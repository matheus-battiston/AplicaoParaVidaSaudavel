package br.com.cwi.crescer.api.security.controller;

import br.com.cwi.crescer.api.controller.request.UsuarioRequest;
import br.com.cwi.crescer.api.controller.response.PesoResponse;
import br.com.cwi.crescer.api.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.controller.request.*;
import br.com.cwi.crescer.api.security.controller.response.ConversaResponse;
import br.com.cwi.crescer.api.security.controller.response.InformacoesPrivadasUsuarioResponse;
import br.com.cwi.crescer.api.security.controller.response.MensagemResponse;
import br.com.cwi.crescer.api.security.controller.response.UsuarioPublicoResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.*;
import br.com.cwi.crescer.api.service.BuscarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/usuarios")
public class UsuarioSecurityController {

    @Autowired
    private IncluirUsuarioService service;
    @Autowired
    private DefinirInformacoesIniciaisService definirInformacoesIniciaisService;
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private SeguirUsuarioService seguirUsuarioService;
    @Autowired
    private UnfollowUsuarioService unfollowUsuarioService;

    @Autowired
    private PesquisarUsuarioPorNomeService pesquisarUsuarioPorNomeService;

    @Autowired
    private ExibirInformacoesPublicasService exibirInformacoesPublicasService;

    @Autowired
    private DefinirGeneroService definirGeneroService;

    @Autowired
    private EditarUsuarioService editarUsuarioService;

    @Autowired
    private EditarRecomendacaoAguaService editarRecomendacaoAguaService;

    @Autowired
    private EditarRecomendacaoCaloriasService editarRecomendacaoCaloriasService;

    @Autowired
    private EditarRecomendacaoProteinasService editarRecomendacaoProteinasService;

    @Autowired
    private EditarRecomendacaoCarboidratosService editarRecomendacaoCarboidratosService;

    @Autowired
    private EditarRecomendacaoLipidiosService editarRecomendacaoLipidiosService;

    @Autowired
    private DefinirFotoPerfilService definirFotoPerfilService;

    @Autowired
    private DefinirMetaService definirMetaService;

    @Autowired
    private DefinirAtividadeFisicaService definirAtividadeFisicaService;

    @Autowired
    private HistoricoPesoService historicoPesoService;

    @Autowired
    private AdicionarPesoService adicionarPesoService;

    @Autowired
    private InformacoesPrivadasUsuarioService informacoesPrivadasUsuarioService;

    @Autowired
    private ListarConversasService listarConversasService;

    @Autowired
    private ListarMensagensService listarMensagensService;

    @Autowired
    private AdicionarConversaService adicionarConversaService;

    @Autowired
    private DefinirTituloService definirTituloService;

    @PostMapping
    public UsuarioResponse incluir(@Valid @RequestBody UsuarioRequest request) {
        return service.incluir(request);
    }
    @PostMapping("/infoIniciais")
    public UsuarioResponse definirInfosIniciais(@Valid @RequestBody InformacoesIniciaisRequest request) {
        return definirInformacoesIniciaisService.definir(request);
    }

    @GetMapping("/me")
    public Usuario buscar() {
        return buscarUsuarioService.logado();
    }

    @GetMapping("/me/info")
    public InformacoesPrivadasUsuarioResponse infosPrivadas(){
        return informacoesPrivadasUsuarioService.informar();
    }

    @PostMapping("/seguir/{idUsuario}")
    @ResponseStatus(OK)
    public void seguirUsuario(@PathVariable Long idUsuario){
        seguirUsuarioService.seguir(idUsuario);
    }

    @PostMapping("/unfollow/{idUsuario}")
    @ResponseStatus(OK)
    public void DeixarDeSeguirUsuario(@PathVariable Long idUsuario){
        unfollowUsuarioService.unfollow(idUsuario);
    }

    @GetMapping("/pesquisa")
    public Page<UsuarioPublicoResponse> pesquisarUsuarioPorNome(@RequestParam("text") Optional<String> nome, Pageable pageable) {
        String paramValue = nome.orElse("");
        return pesquisarUsuarioPorNomeService.pesquisar(paramValue, pageable);
    }

    @GetMapping("/info/{idUsuario}")
    public UsuarioPublicoResponse exibirInfosPublicas(@PathVariable Long idUsuario){
        return exibirInformacoesPublicasService.exibir(idUsuario);
    }

    @PutMapping("/me/nome")
    public void editarNome(@Valid @RequestBody EditarNomeRequest request){
        editarUsuarioService.nome(request);
    }

    @PutMapping("/me/data-nascimento")
    public void alterarDataNascimento(@Valid @RequestBody EditarDataNascimentoRequest request){
        editarUsuarioService.data(request);
    }

    @PutMapping("/me/email")
    public void alterarEmail(@Valid @RequestBody EditarEmailRequest request){
        editarUsuarioService.email(request);
    }

    @PutMapping("/me/agua")
    @ResponseStatus(OK)
    public void editarAguaRecomendada(@Valid @RequestBody EditarRecomendacaoRequest request){
        editarRecomendacaoAguaService.editar(request);
    }

    @PutMapping("/me/calorias")
    @ResponseStatus(OK)
    public void editarCaloriasRecomendadas(@Valid @RequestBody EditarRecomendacaoRequest request){
        editarRecomendacaoCaloriasService.editar(request);
    }

    @PutMapping("/me/proteinas")
    @ResponseStatus(OK)
    public void editarProteinasRecomendadas(@Valid @RequestBody EditarRecomendacaoRequest request){
        editarRecomendacaoProteinasService.editar(request);
    }

    @PutMapping("/me/carboidratos")
    @ResponseStatus(OK)
    public void editarCarboidratosRecomendados(@Valid @RequestBody EditarRecomendacaoRequest request){
        editarRecomendacaoCarboidratosService.editar(request);
    }

    @PutMapping("/me/lipidios")
    @ResponseStatus(OK)
    public void editarLipidiosRecomendados(@Valid @RequestBody EditarRecomendacaoRequest request){
        editarRecomendacaoLipidiosService.editar(request);
    }


    @PostMapping("/me/altura")
    public void alterarAltura(@Valid @RequestBody EditarAlturaRequest request) {
        editarUsuarioService.altura(request);
    }

    @PostMapping("/me/genero")
    public void definirGenero(@Valid @RequestBody DefinirGeneroRequest request) {
        definirGeneroService.definir(request);
    }

    @PostMapping("/me/foto")
    public void definirFoto (@Valid @RequestBody DefinirFotoPerfilRequest request){
        definirFotoPerfilService.definir(request);
    }

    @PostMapping("/me/meta")
    public void definirMeta(@Valid @RequestBody DefinirMetaRequest request){
        definirMetaService.definirMeta(request);
    }

    @PostMapping("/me/atividade")
    public void definirAtividadeFisica(@Valid @RequestBody DefinirAtivdadeRequest request){
        definirAtividadeFisicaService.definir(request);
    }
    @GetMapping("/me/historico-pesos")
    public List<PesoResponse> historicoDePesos(){
        return historicoPesoService.gerar();
    }

    @PostMapping("/me/peso")
    public void adicionarPeso(@Valid @RequestBody AdicionarPesoRequest request){
        adicionarPesoService.adicionar(request);
    }

    @GetMapping("/me/conversas")
    public List<ConversaResponse> listarConversas(){
        return listarConversasService.listar();
    }

    @PutMapping("/me/conversas/adicionar/{idUsuario}")
    public void adicionarConversa(@PathVariable Long idUsuario){
        adicionarConversaService.adicionar(idUsuario);
    }

    @GetMapping("/me/mensagens/{idConversa}")
    public List<MensagemResponse> listarMensagens(@PathVariable Long idConversa){
        return listarMensagensService.listar(idConversa);
    }

    @PutMapping("/me/titulo/{idConquista}")
    public void setarTitulo(@PathVariable Long idConquista){
        definirTituloService.definir(idConquista);
    }
}
