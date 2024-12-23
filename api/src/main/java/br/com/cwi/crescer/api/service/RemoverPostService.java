package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.repository.PostRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.ValidatorUsuarioDonoDoPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoverPostService {

    @Autowired
    private BuscarPostService buscarPostService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ValidatorUsuarioDonoDoPost validatorUsuarioDonoDoPost;

    @Transactional
    public void remover(Long idPost) {
        Usuario usuario = usuarioAutenticadoService.get();
        Post post = buscarPostService.porId(idPost);

        validatorUsuarioDonoDoPost.dono(usuario.getPosts(), post);

        post.limpeza();

        postRepository.delete(post);
    }
}