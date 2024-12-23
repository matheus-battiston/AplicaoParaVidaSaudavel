package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListarCurtidasPostResponse;
import br.com.cwi.crescer.api.controller.response.ListarPessoaResponse;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.mapper.ListarPessoaMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarCurtidasPostService {
    @Autowired
    private BuscarPostService buscarPostService;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public ListarCurtidasPostResponse listar(Long idPost) {

        Usuario usuario = usuarioAutenticadoService.get();
        Post post = buscarPostService.porIdComAcesso(idPost, usuario);

        List<ListarPessoaResponse> curtiram = post.getUsuariosCurtiram().stream()
                .map(ListarPessoaMapper::toResponse)
                .collect(Collectors.toList());

        return ListarCurtidasPostResponse.builder()
                .pessoasCurtida(curtiram)
                .build();
    }
}
