package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListarPessoaResponse;
import br.com.cwi.crescer.api.controller.response.PostResponse;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.PostFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PostResponseMapperTest {
    @InjectMocks
    private PostResponseMapper tested;

    @Test
    @DisplayName("Deve mapear corretamente")
    public void deveMapearPost(){
        Post post = PostFactory.get();
        post.setDataInclusao(LocalDateTime.now());
        ListarPessoaResponse pessoaResponse = ListarPessoaMapper.toResponse(post.getUsuario());

        PostResponse response = PostResponseMapper.toResponse(post, false);

        assertFalse(response.isCurtido());
        assertEquals(response.getId(), post.getId());
        assertEquals(response.getTexto(), post.getTexto());
        assertEquals(response.getImagem(), post.getImagem());
        assertEquals(response.getNroComentarios(), 0);
        assertEquals(response.getNroCurtidas(), 0);
        assertEquals(response.getAutor().getId(), pessoaResponse.getId());
    }
}
