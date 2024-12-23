package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.config.BuscarUsuarioSecuritySerivce;
import br.com.cwi.crescer.api.security.controller.request.AlterarSenhaRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.AlterarSenhaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class AlterarSenhaServiceTest {
    @InjectMocks
    private AlterarSenhaService tested;

    @Mock
    private BuscarUsuarioSecuritySerivce buscarUsuarioSecuritySerivce;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @DisplayName("Deve alterar a senha de um usuario")
    void deveAlterarSenha(){
        String emailDoUsuario = "Email do usuario";
        String token = "TOKEN";
        String novaSenha = "NOVASENHA";
        Usuario usuario = UsuarioFactory.get();
        usuario.setEmail(emailDoUsuario);
        usuario.setTokenSenha(token);
        AlterarSenhaRequest request = new AlterarSenhaRequest();
        request.setSenha(novaSenha);
        request.setEmail(emailDoUsuario);
        request.setToken(token);

        when(buscarUsuarioSecuritySerivce.porEmailEToken(request.getEmail(), request.getToken())).thenReturn(usuario);
        when(passwordEncoder.encode(request.getSenha())).thenReturn(novaSenha);

        tested.alterar(request);

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());

        assertNull(usuarioArgumentCaptor.getValue().getTokenSenha());
        assertEquals(novaSenha, usuarioArgumentCaptor.getValue().getSenha());
    }

    @Test
    @DisplayName("Nao deve alterar nada se nao conseguir validar email e token")
    void naoAlterarSenha(){

        String emailDoUsuario = "Email do usuario";
        String token = "TOKEN";
        String novaSenha = "NOVASENHA";

        AlterarSenhaRequest request = new AlterarSenhaRequest();
        request.setSenha(novaSenha);
        request.setEmail(emailDoUsuario);
        request.setToken(token);

        doThrow(ResponseStatusException.class).when(buscarUsuarioSecuritySerivce).porEmailEToken(emailDoUsuario, token);


        assertThrows(ResponseStatusException.class, () -> {
            tested.alterar(request);
        });

        verify(usuarioRepository, never()).save(any());    }
}
