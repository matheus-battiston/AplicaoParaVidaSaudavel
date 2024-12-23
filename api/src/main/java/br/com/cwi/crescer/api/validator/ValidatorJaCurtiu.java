package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class ValidatorJaCurtiu {
    public static final String JA_CURTIU = "Post ja foi curtido";
    public static final String NAO_CURTIU = "Post nao foi curtido";


    public void jaCurtiu(Post post, List<Post> curtidasPost) {
        if (curtidasPost.contains(post)){
            throw new ResponseStatusException(BAD_REQUEST, JA_CURTIU);
        }
    }

    public void naoCurtiu(Post post, List<Post> curtidasPost){
        if (!curtidasPost.contains(post)){
            throw new ResponseStatusException(BAD_REQUEST, NAO_CURTIU);
        }
    }
}
