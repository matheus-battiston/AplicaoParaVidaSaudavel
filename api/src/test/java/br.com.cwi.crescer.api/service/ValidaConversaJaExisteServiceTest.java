package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Conversa;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ConversaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.ValidaConversaJaExisteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static br.com.cwi.crescer.api.security.service.ValidaConversaJaExisteService.MENSAGEM_CONVERSA_JA_EXISTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidaConversaJaExisteServiceTest {
    @InjectMocks
    private ValidaConversaJaExisteService validaConversaJaExisteService;

    @Mock
    private ConversaRepository conversaRepository;

    @Test
    @DisplayName("Deve retornar ResponseStatusException se a conversa já existir")
    void deveRetornarExceptionSeConversaJaExiste(){
        Usuario usuario1 = UsuarioFactory.get();
        Usuario usuario2 = UsuarioFactory.get();

        Conversa conversa = new Conversa();
        conversa.setPrimeiroUsuario(usuario1);
        conversa.setSegundoUsuario(usuario2);

        List<Conversa> conversas = new ArrayList<>();
        conversas.add(conversa);

        when(conversaRepository.findAllByPrimeiroUsuarioOrSegundoUsuario(usuario1, usuario1)).thenReturn(conversas);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> validaConversaJaExisteService
                        .validar(usuario1, usuario2));

        assertEquals(MENSAGEM_CONVERSA_JA_EXISTE, exception.getReason());
    }
}
