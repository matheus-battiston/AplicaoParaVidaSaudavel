package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListarPostsResponse;
import br.com.cwi.crescer.api.controller.response.PostResponse;
import br.com.cwi.crescer.api.mapper.PostResponseMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarMeusPostsService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    public ListarPostsResponse listar() {
        Usuario usuario = usuarioAutenticadoService.get();

        List<PostResponse> postsDoUsuario = usuario.getPosts()
                .stream()
                .map(post -> PostResponseMapper.toResponse(post, usuario.getPostsCurtidos().contains(post)))
                .collect(Collectors.toList());

        return ListarPostsResponse.builder()
                .listaDePosts(postsDoUsuario)
                .build();
    }
}
