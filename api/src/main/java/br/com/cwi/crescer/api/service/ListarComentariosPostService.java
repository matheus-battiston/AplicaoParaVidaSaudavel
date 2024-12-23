package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ComentarioResponse;
import br.com.cwi.crescer.api.domain.ComentarioPost;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.mapper.ComentarioPostMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarComentariosPostService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private BuscarPostService buscarPostService;
    public List<ComentarioResponse> listar(Long idPost) {
        Usuario usuario = usuarioAutenticadoService.get();
        Post post = buscarPostService.porIdComAcesso(idPost, usuario);
        List<ComentarioPost> comentarios = post.getComentariosPost();

        return comentarios.stream()
                .map(ComentarioPostMapper::toResponse)
                .collect(Collectors.toList());

    }
}
