package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.repository.PostRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class BuscarPostService {
    public static final String POST_NAO_ENCONTRADO = "Post nao foi encontrado";
    public static final String NAO_AUTORIZADO = "Usuario nao tem acesso ao post";
    @Autowired
    private PostRepository postRepository;
    public Post porId(Long id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(UNPROCESSABLE_ENTITY, POST_NAO_ENCONTRADO));
    }

    public Post porIdComAcesso(Long idPost, Usuario usuario){
        return postRepository.findAllowedById(idPost, usuario)
                .orElseThrow(() -> new ResponseStatusException(UNPROCESSABLE_ENTITY, NAO_AUTORIZADO));
    }
}
