package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.NovoPostRequest;
import br.com.cwi.crescer.api.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.mapper.NovoPostMapper.toEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class NovoPostMapperTest {
    @Test
    @DisplayName("Deve retornar um post")
    void deveRetornarUmPost(){
        String imagem = "Imagem do post";
        String texto = "Texto do post";
        boolean privado = false;


        NovoPostRequest request = NovoPostRequest.builder()
                .texto(texto)
                .privado(privado)
                .url(imagem)
                .build();

        Post post = toEntity(request);

        assertEquals(post.getImagem(), imagem);
        assertEquals(post.getTexto(), texto);
        assertEquals(post.isPrivado(), privado);
    }

    @Test
    @DisplayName("Deve retornar campo de imagem nulo quando este nao for passado")
    void deveRetornarImagemNulo(){
        String texto = "Texto do post";
        boolean privado = false;


        NovoPostRequest request = NovoPostRequest.builder()
                .texto(texto)
                .privado(privado)
                .build();

        Post post = toEntity(request);

        assertNull(post.getImagem());
        assertEquals(post.getTexto(), texto);
        assertEquals(post.isPrivado(), privado);
    }
}
