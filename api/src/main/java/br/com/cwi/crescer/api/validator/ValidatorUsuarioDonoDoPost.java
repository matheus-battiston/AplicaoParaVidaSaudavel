package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class ValidatorUsuarioDonoDoPost {
    public final static String NAO_EH_DONO = "Este usuario nao pode excluir este post";
    public void dono(List<Post> posts, Post post) {
        if (!posts.contains(post)){
            throw new ResponseStatusException(BAD_REQUEST, NAO_EH_DONO);
        }
    }
}
