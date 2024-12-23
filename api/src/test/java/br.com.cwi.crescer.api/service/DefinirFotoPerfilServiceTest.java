package br.com.cwi.crescer.api.service;


import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.DefinirFotoPerfilRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.DefinirFotoPerfilService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefinirFotoPerfilServiceTest {

    @InjectMocks
    private DefinirFotoPerfilService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @DisplayName("Deve definir uma foto de perfil")
    void deveDefinirFotoPerfil(){
        Usuario usuario = UsuarioFactory.get();
        String url = "URL";
        DefinirFotoPerfilRequest request = new DefinirFotoPerfilRequest();
        request.setUrlFoto(url);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.definir(request);

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        Usuario usuarioCapturado = usuarioArgumentCaptor.getValue();

        assertEquals(url, usuarioCapturado.getImagemPerfil());
    }

}
