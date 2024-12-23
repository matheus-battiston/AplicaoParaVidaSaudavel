package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.RefeicaoResponse;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.RefeicaoFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ListarRefeicoesServiceTest {
    @InjectMocks
    private ListarRefeicoesService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve fazer uma lista de receitas do usuario")
    void deveListarReceitasDoUsuario(){
        Usuario usuario = UsuarioFactory.get();

        Refeicao refeicaoUm = RefeicaoFactory.getBuilder();
        Refeicao refeicaoDois = RefeicaoFactory.getBuilder();
        Alimento alimento = AlimentoFactory.getBuilder();
        RefeicaoAlimento refeicaoAlimento = new RefeicaoAlimento();
        refeicaoAlimento.setQuantidade("100");
        refeicaoAlimento.setAlimento(alimento);
        refeicaoUm.getAlimentos().add(refeicaoAlimento);

        usuario.getRefeicoes().add(refeicaoUm);
        usuario.getRefeicoes().add(refeicaoDois);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        List<RefeicaoResponse> response = tested.listar(LocalDate.now());


        assertEquals(2, response.size());
        assertEquals(refeicaoUm.getId(), response.get(0).getId());
        assertEquals(refeicaoDois.getId(), response.get(1).getId());
        assertEquals(alimento.getId(), response.get(0).getAlimentos().get(0).getId());
    }


    @Test
    @DisplayName("Deve fazer uma lista vazia se nao tiver refeicoes")
    void deveFazerListaVazia(){
        Usuario usuario = UsuarioFactory.get();


        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        List<RefeicaoResponse> response = tested.listar(LocalDate.now());


        assertTrue(response.isEmpty());
    }

}
