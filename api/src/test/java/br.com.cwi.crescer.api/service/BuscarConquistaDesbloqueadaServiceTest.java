package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.factories.ConquistaFactory;
import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ConquistaUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.BuscarConquistaDesbloqueadaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static br.com.cwi.crescer.api.security.service.BuscarConquistaDesbloqueadaService.NAO_EXISTE_ESTA_CONQUISTA_DESBLOQUADA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarConquistaDesbloqueadaServiceTest {

    @InjectMocks
    private BuscarConquistaDesbloqueadaService tested;

    @Mock
    private ConquistaUsuarioRepository conquistaUsuarioRepository;

    @Test
    @DisplayName("Deve retornar um erro quando conquista nao existe")
    void deveRetornarErro(){
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porIdConquista(new Usuario(), SimpleFactory.getRandomLong()));

        assertEquals(NAO_EXISTE_ESTA_CONQUISTA_DESBLOQUADA, exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar a conquista correta quando pesquisado")
    public void deveRetornarPostCerto(){
        Usuario usuario = UsuarioFactory.get();
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        Conquista conquista = ConquistaFactory.get();
        conquistaUsuario.setConquista(conquista);

        when(conquistaUsuarioRepository.findFirstByUsuarioAndDesbloqueadaIsTrueAndConquista_Id(usuario, conquista.getId()))
                .thenReturn(Optional.of(conquistaUsuario));

        ConquistaUsuario response = tested.porIdConquista(usuario, conquista.getId());

        Assertions.assertEquals(conquista.getId(),response.getConquista().getId());
    }
}
