package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.UsuarioRequest;
import br.com.cwi.crescer.api.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.factories.ConquistaFactory;
import br.com.cwi.crescer.api.repository.ConquistaRepository;
import br.com.cwi.crescer.api.repository.PesoRepository;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.AtividadeFisica;
import br.com.cwi.crescer.api.security.domain.Meta;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.IncluirUsuarioService;
import br.com.cwi.crescer.api.security.service.ValidarEmailUnicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncluirUsuarioServiceTest {
    @InjectMocks
    private IncluirUsuarioService tested;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ValidarEmailUnicoService validarEmailUnicoService;
    @Mock
    private PesoRepository pesoRepository;

    @Mock
    private ConquistaRepository conquistaRepository;

    @Test
    @DisplayName("Deve adicionar um usuario")
    void deveAdicionarUsuario(){
        String nome = "nome";
        String email = "email";
        String senha = "senha";
        LocalDate dataNascimento = LocalDate.now();
        List<Conquista> conqusitas = List.of(ConquistaFactory.get());
        UsuarioRequest request = new UsuarioRequest();
        request.setNome(nome);
        request.setEmail(email);
        request.setSenha(senha);
        request.setDataNascimento(dataNascimento);

        Usuario usuario = Usuario.builder()
                .nome(nome)
                .pontuacao("0")
                .senha(senha)
                .dataNascimento(dataNascimento)
                .ativo(true)
                .meta(Meta.M)
                .atividadeFisica(AtividadeFisica.P)
                .email(email)
                .build();

        when(conquistaRepository.findAll()).thenReturn(conqusitas);
        when(passwordEncoder.encode(senha)).thenReturn(senha);

        UsuarioResponse response = tested.incluir(request);

        verify(usuarioRepository).save(usuario);

        assertEquals(nome, response.getNome());
        assertEquals(email, response.getEmail());
    }

    @Test
    @DisplayName("Nao deve adicionar usuario se email nao for unico")
    void naoAdicionarUsuario(){
        String nome = "nome";
        String email = "email";
        String senha = "senha";
        LocalDate dataNascimento = LocalDate.now();
        UsuarioRequest request = new UsuarioRequest();
        request.setNome(nome);
        request.setEmail(email);
        request.setSenha(senha);
        request.setDataNascimento(dataNascimento);

        List<Conquista> conqusitas = List.of(ConquistaFactory.get());
        when(conquistaRepository.findAll()).thenReturn(conqusitas);

        doThrow(ResponseStatusException.class).when(validarEmailUnicoService).validar(email);

        assertThrows(ResponseStatusException.class, () -> {
            tested.incluir(request);
        });

        verify(usuarioRepository, never()).save(any());
    }

}
