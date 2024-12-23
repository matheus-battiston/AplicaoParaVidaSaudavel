package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import br.com.cwi.crescer.api.factories.RefeicaoFactory;
import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.RefeicaoAlimentoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static br.com.cwi.crescer.api.factories.AlimentoFactory.getBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarAlimentosRecentementeUsadosServiceTest {
    @InjectMocks
    private ListarAlimentosRecentementeUsadosService listarAlimentosRecentementeUsadosService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    @Test
    @DisplayName("Deve listar os alimentos recentes do usuário corretamente")
    void deveListarAlimentosRecentes(){
        Usuario usuario = UsuarioFactory.get();
        Refeicao refeicao = RefeicaoFactory.getBuilder();
        List<RefeicaoAlimento> alimentosUsuario = new ArrayList<>();

        RefeicaoAlimento refeicaoAlimento = new RefeicaoAlimento();
        refeicaoAlimento.setId(SimpleFactory.getRandomLong());
        refeicaoAlimento.setAlimento(getBuilder());
        refeicaoAlimento.setQuantidade("100");
        refeicaoAlimento.setRefeicao(refeicao);

        RefeicaoAlimento refeicaoAlimento2 = new RefeicaoAlimento();
        refeicaoAlimento2.setId(SimpleFactory.getRandomLong());
        refeicaoAlimento2.setAlimento(getBuilder());
        refeicaoAlimento2.setQuantidade("100");
        refeicaoAlimento2.setRefeicao(refeicao);

        RefeicaoAlimento refeicaoAlimento3 = new RefeicaoAlimento();
        refeicaoAlimento3.setId(SimpleFactory.getRandomLong());
        refeicaoAlimento3.setAlimento(getBuilder());
        refeicaoAlimento3.setQuantidade("100");
        refeicaoAlimento3.setRefeicao(refeicao);

        RefeicaoAlimento refeicaoAlimento4 = new RefeicaoAlimento();
        refeicaoAlimento4.setId(SimpleFactory.getRandomLong());
        refeicaoAlimento4.setAlimento(getBuilder());
        refeicaoAlimento4.setQuantidade("100");
        refeicaoAlimento4.setRefeicao(refeicao);

        RefeicaoAlimento refeicaoAlimento5 = new RefeicaoAlimento();
        refeicaoAlimento5.setId(SimpleFactory.getRandomLong());
        refeicaoAlimento5.setAlimento(getBuilder());
        refeicaoAlimento5.setQuantidade("100");
        refeicaoAlimento5.setRefeicao(refeicao);

        RefeicaoAlimento refeicaoAlimento6 = new RefeicaoAlimento();
        refeicaoAlimento6.setId(SimpleFactory.getRandomLong());
        refeicaoAlimento6.setAlimento(getBuilder());
        refeicaoAlimento6.setQuantidade("100");
        refeicaoAlimento6.setRefeicao(refeicao);


        refeicao.adicionarAlimento(refeicaoAlimento);
        refeicao.adicionarAlimento(refeicaoAlimento2);
        refeicao.adicionarAlimento(refeicaoAlimento3);
        refeicao.adicionarAlimento(refeicaoAlimento4);
        refeicao.adicionarAlimento(refeicaoAlimento5);
        refeicao.adicionarAlimento(refeicaoAlimento6);

        alimentosUsuario.add(refeicaoAlimento);
        alimentosUsuario.add(refeicaoAlimento2);
        alimentosUsuario.add(refeicaoAlimento3);
        alimentosUsuario.add(refeicaoAlimento4);
        alimentosUsuario.add(refeicaoAlimento5);
        alimentosUsuario.add(refeicaoAlimento6);
        usuario.adicionarRefeicao(refeicao);
        refeicao.setUsuario(usuario);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        when(refeicaoAlimentoRepository.findAllRecentlyUsed(usuario)).thenReturn(alimentosUsuario);

        List<AlimentoResponse> response = listarAlimentosRecentementeUsadosService.listar();

        assertEquals(usuario.getRefeicoes().get(0).getAlimentos().get(0).getAlimento().getId(), response.get(0).getId());
        assertEquals(usuario.getRefeicoes().get(0).getAlimentos().get(1).getAlimento().getId(), response.get(1).getId());
        assertEquals(usuario.getRefeicoes().get(0).getAlimentos().get(2).getAlimento().getId(), response.get(2).getId());
        assertEquals(usuario.getRefeicoes().get(0).getAlimentos().get(3).getAlimento().getId(), response.get(3).getId());
        assertEquals(usuario.getRefeicoes().get(0).getAlimentos().get(4).getAlimento().getId(), response.get(4).getId());
        assertEquals(5, response.size());
    }

    @Test
    @DisplayName("Deve retornar vazio se o usuário não possui refeições")
    void deveRetornarVazioSeUsuarioNaoPossuiRefeicoes(){
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(refeicaoAlimentoRepository.findAllRecentlyUsed(usuario)).thenReturn(new ArrayList<>());

        List<AlimentoResponse> response = listarAlimentosRecentementeUsadosService.listar();

        assertEquals(usuario.getRefeicoes().size(), response.size());
    }

}
