package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.NovoComentarioRequest;
import br.com.cwi.crescer.api.controller.response.ComentarioResponse;
import br.com.cwi.crescer.api.domain.ComentarioPost;
import br.com.cwi.crescer.api.domain.Post;
import br.com.cwi.crescer.api.factories.ComentarioFactory;
import br.com.cwi.crescer.api.factories.PostFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class ComentarioPostMapperTest {
    @Test
    @DisplayName("Deve mapear corretamente para um comentario")
    void deveMapearCorretamente(){
        String texto = "Texto do comentario";
        Post post = PostFactory.get();
        Long idPost = getRandomLong();
        post.setId(idPost);

        Usuario usuario = UsuarioFactory.get();
        NovoComentarioRequest novoComentarioRequest = new NovoComentarioRequest();
        novoComentarioRequest.setTexto(texto);

        ComentarioPost comentario = ComentarioPostMapper.toEntity(texto, usuario,post);


        assertEquals(comentario.getPost(), post);
        assertEquals(comentario.getUsuario(), usuario);
        assertEquals(comentario.getTexto(), texto);

    }

    @Test
    @DisplayName("Deve mapear corretamente a entidade para um response")
    void deveMapearParaResponse(){
        Usuario usuario = UsuarioFactory.get();
        ComentarioPost comentario = ComentarioFactory.get();
        comentario.setUsuario(usuario);

        ComentarioResponse response = ComentarioPostMapper.toResponse(comentario);

        assertEquals(comentario.getId(), response.getId());
        assertEquals(comentario.getTexto(), response.getComentario());
        assertEquals(comentario.getUsuario().getId(), response.getUsuario().getId());
    }
}
