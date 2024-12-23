package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.PostResponse;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.repository.PostRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.mapper.PostResponseMapper.toResponse;

@Service
public class ListarPostDeUmUsuarioService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private PostRepository postRepository;
    public Page<PostResponse> listar(Long idUsuario, Pageable pageable) {
        Usuario usuarioAutenticado = usuarioAutenticadoService.get();
        Page<Post> postsPaginados = postRepository.findPostsDoUsuarioPermitido(idUsuario, usuarioAutenticado, pageable);
        return postsPaginados.map(post -> toResponse(post, usuarioAutenticado.getPostsCurtidos().contains(post)));
    }
}
