package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.response.UsuarioPublicoResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.PesquisarUsuarioPorNomeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PesquisarUsuarioPorNomeServiceTest {
    @InjectMocks
    private PesquisarUsuarioPorNomeService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar uma lista de usuarios")
    void deveRetornarListaDeUsuarios(){
        String pesquisa = "Usuario";
        Usuario usuarioUm = UsuarioFactory.get();
        Usuario usuarioDois = UsuarioFactory.get();
        Pageable pageable = PageRequest.of(0,5);
        List<Usuario> listaUsuarios = List.of(usuarioUm, usuarioDois);
        Page<Usuario> postsPaginados = new PageImpl<>(listaUsuarios);

        when(usuarioRepository.findByNomeContainsIgnoreCase(pesquisa, pageable))
                .thenReturn(postsPaginados);

        Page<UsuarioPublicoResponse> response = tested.pesquisar(pesquisa, pageable);

        assertEquals(2, response.getContent().size());
        assertEquals(usuarioUm.getId(), response.getContent().get(0).getId());
        assertEquals(usuarioDois.getId(), response.getContent().get(1).getId());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia se nao encontrar ninguem")
    void deveRetornarListaVazia(){
        String pesquisa = "NOME ALEATORIO";
        Pageable pageable = PageRequest.of(0,5);

        when(usuarioRepository.findByNomeContainsIgnoreCase(pesquisa, pageable))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        Page<UsuarioPublicoResponse> response = tested.pesquisar(pesquisa, pageable);

        assertTrue(response.isEmpty());
    }

}
