package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.EditarAlturaRequest;
import br.com.cwi.crescer.api.security.controller.request.EditarDataNascimentoRequest;
import br.com.cwi.crescer.api.security.controller.request.EditarEmailRequest;
import br.com.cwi.crescer.api.security.controller.request.EditarNomeRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.EditarUsuarioService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EditarUsuarioServiceTest {
    @InjectMocks
    private EditarUsuarioService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @DisplayName("Deve editar o nome de um usuario")
    void editarNome(){
        String novoNome = "Nome editado";
        Usuario usuario = UsuarioFactory.get();
        EditarNomeRequest request = new EditarNomeRequest();
        request.setNome(novoNome);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.nome(request);

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        Usuario capturado = usuarioArgumentCaptor.getValue();

        assertEquals(novoNome, capturado.getNome());
    }

    @Test
    @DisplayName("Deve editar a data de nascimento de um usuario")
    void editarData(){
        LocalDate novaData = LocalDate.of(1997, 9, 17);
        Usuario usuario = UsuarioFactory.get();
        EditarDataNascimentoRequest request = new EditarDataNascimentoRequest();
        request.setData(novaData);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.data(request);

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        Usuario capturado = usuarioArgumentCaptor.getValue();

        assertEquals(novaData, capturado.getDataNascimento());
    }

    @Test
    @DisplayName("Deve editar o email de um usuario")
    void editarEmail(){
        String novoEmail = "usuarioemail@cwi.com.br";
        Usuario usuario = UsuarioFactory.get();
        EditarEmailRequest request = new EditarEmailRequest();
        request.setEmail(novoEmail);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.email(request);

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        Usuario capturado = usuarioArgumentCaptor.getValue();

        assertEquals(novoEmail, capturado.getEmail());
    }

    @Test
    @DisplayName("Deve editar a altura e atualizar todas as infos")
    void editarAltura(){
        String imcEsperado = "25,00";
        Integer novaAltura = 180;
        String pesoUsuario = "81";
        Usuario usuario = UsuarioFactory.get();
        usuario.setPeso(pesoUsuario);

        EditarAlturaRequest request = new EditarAlturaRequest();
        request.setAltura(novaAltura);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        tested.altura(request);

        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());
        verify(calcularRecomendacoesUsuarioService).calcular();
        Usuario capturado = usuarioArgumentCaptor.getValue();

        assertEquals(novaAltura, capturado.getAltura());
    }

}
