package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.EditarPrivacidadeRequest;
import br.com.cwi.crescer.api.controller.request.NovoComentarioRequest;
import br.com.cwi.crescer.api.controller.request.NovoPostRequest;
import br.com.cwi.crescer.api.controller.response.ComentarioResponse;
import br.com.cwi.crescer.api.controller.response.ListarCurtidasPostResponse;
import br.com.cwi.crescer.api.controller.response.ListarPostsResponse;
import br.com.cwi.crescer.api.controller.response.PostResponse;
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
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostarService postarService;

    @Autowired
    private EditarPrivacidadeService editarPrivacidadeService;

    @Autowired
    private CurtirPostService curtirPostService;

    @Autowired
    private RemoverCurtidaService removerCurtidaService;

    @Autowired
    private RemoverPostService removerPostService;

    @Autowired
    private ListarCurtidasPostService listarCurtidasPostService;

    @Autowired
    private ListarMeusPostsService listarMeusPostsService;
    @Autowired
    private PostarNovoComentarioService postarNovoComentarioService;

    @Autowired
    private ListarComentariosPostService listarComentariosPostService;

    @Autowired
    private ListarPostsSeguindoService listarPostsSeguindoService;

    @Autowired
    private ListarPostDeUmUsuarioService listarPostDeUmUsuarioService;


    @PostMapping()
    @ResponseStatus(CREATED)
    public void postar(@Valid @RequestBody NovoPostRequest request){
        postarService.postar(request);
    }

    @PutMapping("/{idPost}")
    @ResponseStatus(OK)
    public void editar(@Valid @RequestBody EditarPrivacidadeRequest request, @PathVariable Long idPost){
        editarPrivacidadeService.editar(request, idPost);
    }

    @PutMapping("/{idPost}/curtir")
    @ResponseStatus(OK)
    public void curtir(@PathVariable Long idPost){
        curtirPostService.curtir(idPost);
    }

    @PutMapping("/{idPost}/descurtir")
    @ResponseStatus(OK)
    public void removerCurtida(@PathVariable Long idPost) {
        removerCurtidaService.remover(idPost);
    }

    @DeleteMapping("/{idPost}")
    @ResponseStatus(OK)
    public void removerPost(@PathVariable Long idPost) {removerPostService.remover(idPost);}

    @GetMapping("/{idPost}/curtidas")
    public ListarCurtidasPostResponse listarCurtidas(@PathVariable Long idPost) {
        return listarCurtidasPostService.listar(idPost);
    }

    @GetMapping("/me")
    public ListarPostsResponse listarMeusPosts(){
        return listarMeusPostsService.listar();
    }

    @PostMapping("/{postId}")
    public void fazerNovoComentario(@Valid @RequestBody NovoComentarioRequest request, @PathVariable Long postId){
        postarNovoComentarioService.postar(request, postId);
    }

    @GetMapping("/{idPost}/comentarios")
    public List<ComentarioResponse> listarComentarios(@PathVariable Long idPost){
        return listarComentariosPostService.listar(idPost);
    }

    @GetMapping()
    public Page<PostResponse> listarPostsSeguindo(Pageable pageable){
        return listarPostsSeguindoService.listar(pageable);
    }

    @GetMapping("/usuario/{idUsuario}")
    public Page<PostResponse> listarPostsUsuario(@PathVariable Long idUsuario, Pageable pageable){
        return listarPostDeUmUsuarioService.listar(idUsuario, pageable);
    }
}
