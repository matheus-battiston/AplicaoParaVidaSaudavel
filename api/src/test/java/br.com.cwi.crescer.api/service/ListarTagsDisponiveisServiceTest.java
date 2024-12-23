package br.com.cwi.crescer.api.service;


import br.com.cwi.crescer.api.controller.response.TagsResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.factories.ConquistaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ConquistaUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ListarTagsDisponiveisServiceTest {
    @InjectMocks
    private ListarTagsDisponiveisService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ConquistaUsuarioRepository conquistaUsuarioRepository;

    @Test
    @DisplayName("Deve retornar uma lista de tags response")
    void deveRetornarLista(){
        Conquista conquista = ConquistaFactory.get();
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setConquista(conquista);
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(conquistaUsuarioRepository.findAllByUsuarioAndDesbloqueadaIsTrue(usuario)).thenReturn(List.of(conquistaUsuario));


        List<TagsResponse> response = tested.listar();

        assertEquals(1, response.size());
        assertEquals(conquista.getId(), response.get(0).getIdConquista());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia por nao ter conquistas")
    void deveRetornarListaVazia(){
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(conquistaUsuarioRepository.findAllByUsuarioAndDesbloqueadaIsTrue(usuario)).thenReturn(new ArrayList<>());


        List<TagsResponse> response = tested.listar();

        assertTrue(response.isEmpty());
    }
}
