package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ReceitaAlimentoRequest;
import br.com.cwi.crescer.api.controller.request.ReceitaRequest;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.mapper.ReceitaMapper;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static br.com.cwi.crescer.api.domain.TipoConquista.RECEITAS;
import static br.com.cwi.crescer.api.service.CriarReceitaService.PONTOS_POR_NOVA_RECEITA;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarReceitaServiceTest {
    @InjectMocks
    private CriarReceitaService criarReceitaService;
    @Mock
    private BuscarAlimentoService buscarAlimentoService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private ReceitaRepository receitaRepository;
    @Mock
    private AtualizarReceitaService atualizarReceitaService;

    @Mock
    private ConquistasService conquistasService;

    @Mock
    private NowService nowService;

    @Test
    @DisplayName("Deve criar receita")
    void deveCriarReceita() {
        Usuario logado = UsuarioFactory.get();
        ReceitaRequest request = new ReceitaRequest();
        request.setTitulo("teste");
        request.setCopia(false);
        request.setPrivado(false);
        request.setAlimentos(new ArrayList<>());
        request.getAlimentos().add(new ReceitaAlimentoRequest(1L, "200"));
        Receita receita = ReceitaMapper.toEntity(request);

        when(usuarioAutenticadoService.get()).thenReturn(logado);
        when(nowService.nowDate()).thenReturn(LocalDate.now());

        criarReceitaService.criar(request);

        verify(atualizarReceitaService).atualizar(receita);
        verify(receitaRepository).save(receita);
        verify(conquistasService).conquistasQuantidadeDeAcoes(logado, RECEITAS);

        assertTrue(logado.getReceitas().contains(receita));
    }

    @Test
    @DisplayName("Deve adicionar pontos ao usuário ao criar primeira receita do dia")
    void deveAdicionarPontosUsuarioPrimeiraReceitaDia() {
        Usuario logado = UsuarioFactory.get();
        LocalDate hoje = LocalDate.now();

        ReceitaRequest request = new ReceitaRequest();
        request.setTitulo("teste");
        request.setCopia(false);
        request.setPrivado(false);
        request.setAlimentos(new ArrayList<>());
        request.getAlimentos().add(new ReceitaAlimentoRequest(1L, "200"));

        when(usuarioAutenticadoService.get()).thenReturn(logado);
        when(nowService.nowDate()).thenReturn(LocalDate.now());
        when(receitaRepository.existsByUsuarioAndDataCriacao(logado, hoje)).thenReturn(false);

        criarReceitaService.criar(request);

        assertEquals(PONTOS_POR_NOVA_RECEITA, parseInt(logado.getPontuacao()));
    }

    @Test
    @DisplayName("Não deve adicionar pontos ao usuário se não for a primeira receita do dia")
    void naoDeveAdicionarPontosUsuarioSeNaoForPrimeiraReceita() {
        Usuario logado = UsuarioFactory.get();
        LocalDate hoje = LocalDate.now();

        Receita receita = ReceitaFactory.get();
        receita.setCriador(logado);
        logado.adicionarReceita(receita);

        ReceitaRequest request = new ReceitaRequest();
        request.setTitulo("teste");
        request.setCopia(false);
        request.setPrivado(false);
        request.setAlimentos(new ArrayList<>());
        request.getAlimentos().add(new ReceitaAlimentoRequest(1L, "200"));

        when(usuarioAutenticadoService.get()).thenReturn(logado);
        when(nowService.nowDate()).thenReturn(LocalDate.now());
        when(receitaRepository.existsByUsuarioAndDataCriacao(logado, hoje)).thenReturn(true);

        criarReceitaService.criar(request);

        assertEquals(0, parseInt(logado.getPontuacao()));
    }
}
