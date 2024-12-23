package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.ConquistasObtidasResponse;
import br.com.cwi.crescer.api.controller.response.ConquistasProgressoResponse;
import br.com.cwi.crescer.api.controller.response.TagsResponse;
import br.com.cwi.crescer.api.service.ListarConquistasObtidasService;
import br.com.cwi.crescer.api.service.ListarConquistasService;
import br.com.cwi.crescer.api.service.ListarTagsDisponiveisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conquistas")
public class ConquistaController {
    @Autowired
    private ListarConquistasService listarConquistasService;

    @Autowired
    private ListarConquistasObtidasService listarConquistasObtidasService;

    @Autowired
    private ListarTagsDisponiveisService listarTagsDisponiveisService;

    @GetMapping
    private List<ConquistasProgressoResponse> listaConquistas(){
        return listarConquistasService.listar();
    }

    @GetMapping("/{idUsuario}")
    private List<ConquistasObtidasResponse> listarConquistasObtidas(@PathVariable Long idUsuario){
        return listarConquistasObtidasService.listar(idUsuario);
    }

    @GetMapping("/tags-disponiveis")
    private List<TagsResponse> listarTagsDisponiveis(){
        return listarTagsDisponiveisService.listar();
    }
}
