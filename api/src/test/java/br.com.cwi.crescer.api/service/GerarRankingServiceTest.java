package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.PessoaRankingResponse;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
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

import static br.com.cwi.crescer.api.factories.UsuarioFactory.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class GerarRankingServiceTest {
    @InjectMocks
    private GerarRankingService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve gerar uma pagina de usuarios")
    void deveGerarPaginaDeUsuariosOrdenados(){
        List<Usuario> usuarios = List.of(get(), get());
        Pageable pageable = PageRequest.of(0,5);
        Page<Usuario> usuariosPaginados = new PageImpl<>(usuarios);

        when(usuarioRepository.findRanking(pageable)).thenReturn(usuariosPaginados);

        Page<PessoaRankingResponse> responses = tested.gerar(pageable);
        assertEquals(2, responses.getSize());
    }

    @Test
    @DisplayName("Deve gerar uma pagina vazia se nao tiver usuarios")
    void deveGerarPaginaVazia(){
        List<Usuario> usuarios = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,5);
        Page<Usuario> usuariosPaginados = new PageImpl<>(usuarios);

        when(usuarioRepository.findRanking(pageable)).thenReturn(usuariosPaginados);

        Page<PessoaRankingResponse> responses = tested.gerar(pageable);
        assertTrue(responses.isEmpty());
    }
}
