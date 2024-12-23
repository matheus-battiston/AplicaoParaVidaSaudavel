package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class BuscarReceitaServiceTest {

    @InjectMocks
    private BuscarReceitaService tested;

    @Mock
    private ReceitaRepository receitaRepository;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve retornar a receita correta quando pesquisada")
    public void deveRetornarReceitaCerta(){
        Receita receita = ReceitaFactory.get();
        Usuario logado = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(logado);
        when(receitaRepository.findAllowedById(receita.getId(), logado)).thenReturn(Optional.of(receita));

        Receita retorno = tested.porId(receita.getId());

        verify(receitaRepository).findAllowedById(receita.getId(), logado);
        assertEquals(receita, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando nao encontrar receita")
    void deveRetornarErro(){
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Receita não encontrada", exception.getReason());
    }
}
