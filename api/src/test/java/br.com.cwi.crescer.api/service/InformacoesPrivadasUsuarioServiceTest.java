package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.response.InformacoesPrivadasUsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.InformacoesPrivadasUsuarioService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InformacoesPrivadasUsuarioServiceTest {
    @InjectMocks
    private InformacoesPrivadasUsuarioService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar um response com as informaçoes do usuario")
    void deveRetornarInfosDoUsuario(){
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(usuarioRepository.findPosicaoUsuario(usuario.getId())).thenReturn(1L);

        InformacoesPrivadasUsuarioResponse response = tested.informar();

        assertEquals(usuario.getId(),response.getId());
        assertEquals(1L, response.getPosicao());
    }

}
