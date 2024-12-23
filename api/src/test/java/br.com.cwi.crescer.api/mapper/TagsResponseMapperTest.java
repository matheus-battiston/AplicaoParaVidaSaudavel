package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.TagsResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.factories.ConquistaFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagsResponseMapperTest {
    @Test
    @DisplayName("Deve mapear corretamente uma conquista")
    void deveMapearConquistaParaTag(){
        String recompensa = "Recompensa da conquista";

        Conquista conquista = ConquistaFactory.get();
        conquista.setRecompensa(recompensa);
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setConquista(conquista);

        TagsResponse response = TagsResponseMapper.toResponse(conquistaUsuario);

        assertEquals(conquista.getRecompensa(), response.getTag());
        assertEquals(conquista.getId(), response.getIdConquista());
        assertEquals(conquista.getCategoria(), response.getCategoria());
    }
}
