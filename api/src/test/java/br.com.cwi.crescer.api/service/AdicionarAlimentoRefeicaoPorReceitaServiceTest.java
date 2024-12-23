package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.RefeicaoPorReceitaRequest;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.RefeicaoFactory;
import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.mapper.RefeicaoPorReceitaMapper;
import br.com.cwi.crescer.api.repository.RefeicaoAlimentoRepository;
import br.com.cwi.crescer.api.repository.RefeicaoRepository;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static br.com.cwi.crescer.api.domain.Periodo.JA;
import static br.com.cwi.crescer.api.domain.Periodo.LM;
import static br.com.cwi.crescer.api.factories.AlimentoFactory.getBuilder;
import static br.com.cwi.crescer.api.factories.ReceitaFactory.get;
import static br.com.cwi.crescer.api.mapper.RefeicaoPorReceitaMapper.toEntity;
import static java.time.LocalDate.now;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class AdicionarAlimentoRefeicaoPorReceitaServiceTest {
    @InjectMocks
    private AdicionarAlimentoRefeicaoPorReceitaService adicionarAlimentoRefeicaoPorReceitaService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private BuscarReceitaService buscarReceitaService;

    @Mock
    private AtualizarRefeicaoService atualizarRefeicaoService;

    @Mock
    private RefeicaoRepository refeicaoRepository;

    @Mock
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private NowService nowService;

    @Captor
    private ArgumentCaptor<Refeicao> refeicaoCaptor;

    @Mock
    private ConquistasService conquistasService;

    @Test
    @DisplayName("Deve adicionar uma refeicao a partir de uma receita corretamente")
    void deveAdicionarRefeicaoPorReceita(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();

        RefeicaoPorReceitaRequest request = new RefeicaoPorReceitaRequest();
        request.setIdReceita(SimpleFactory.getRandomLong());
        request.setDia(now());
        request.setPeriodo(JA);

        Alimento alimento = getBuilder();

        Receita receita = get();
        receita.setId(request.getIdReceita());
        receita.adicionarAlimento(alimento, "100");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarReceitaService.porId(request.getIdReceita())).thenReturn(receita);
        when(nowService.nowDate()).thenReturn(hoje);

        adicionarAlimentoRefeicaoPorReceitaService.adicionar(request);

        verify(refeicaoRepository).save(refeicaoCaptor.capture());

        Refeicao refeicao = refeicaoCaptor.getValue();

        verify(atualizarRefeicaoService).atualizar(refeicao);
        verify(usuarioRepository).save(usuario);

        assertEquals(alimento, refeicao.getAlimentos().get(0).getAlimento());
        assertEquals(request.getPeriodo(), refeicao.getPeriodo());
        assertEquals(request.getDia(), refeicao.getDia());
    }

    @Test
    @DisplayName("Deve dar pontos ao usuário quando adicionar alimento por receita pela primeira vez no dia (sem streak)")
    void deveDarPontosAoUsuarioAoAdicionarAlimentoPorReceitaSemStreak(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();

        RefeicaoPorReceitaRequest request = new RefeicaoPorReceitaRequest();
        request.setIdReceita(SimpleFactory.getRandomLong());
        request.setDia(now());
        request.setPeriodo(JA);

        Alimento alimento = getBuilder();

        Receita receita = get();
        receita.setId(request.getIdReceita());
        receita.adicionarAlimento(alimento, "100");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarReceitaService.porId(request.getIdReceita())).thenReturn(receita);
        when(nowService.nowDate()).thenReturn(hoje);
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(false);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(false);

        adicionarAlimentoRefeicaoPorReceitaService.adicionar(request);

        verify(refeicaoRepository).save(refeicaoCaptor.capture());
        verify(conquistasService).pontos(usuario);

        Refeicao refeicao = refeicaoCaptor.getValue();

        verify(atualizarRefeicaoService).atualizar(refeicao);
        verify(usuarioRepository).save(usuario);

        assertEquals("20", usuario.getPontuacao());
    }

    @Test
    @DisplayName("Deve dar pontos ao usuário quando adicionar alimento por receita pela primeira vez no dia (com streak)")
    void deveDarPontosAoUsuarioAoAdicionarAlimentoPorReceitaComStreak(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();
        Refeicao refeicaoDeOntem = RefeicaoFactory.getBuilder();
        refeicaoDeOntem.setDia(now().minusDays(1));
        usuario.adicionarRefeicao(refeicaoDeOntem);

        RefeicaoPorReceitaRequest request = new RefeicaoPorReceitaRequest();
        request.setIdReceita(SimpleFactory.getRandomLong());
        request.setDia(now());
        request.setPeriodo(JA);

        Alimento alimento = getBuilder();

        Receita receita = get();
        receita.setId(request.getIdReceita());
        receita.adicionarAlimento(alimento, "100");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarReceitaService.porId(request.getIdReceita())).thenReturn(receita);
        when(nowService.nowDate()).thenReturn(hoje);
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(false);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(true);

        adicionarAlimentoRefeicaoPorReceitaService.adicionar(request);

        verify(refeicaoRepository).save(refeicaoCaptor.capture());

        Refeicao refeicao = refeicaoCaptor.getValue();

        verify(atualizarRefeicaoService).atualizar(refeicao);
        verify(usuarioRepository).save(usuario);

        assertEquals("40", usuario.getPontuacao());
    }

    @Test
    @DisplayName("Não deve dar pontos ao usuário quando adicionar alimento por receita em um dia passado")
    void naoDeveDarPontosAoUsuarioAoAdicionarAlimentoEmDiaPassado(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();

        RefeicaoPorReceitaRequest request = new RefeicaoPorReceitaRequest();
        request.setIdReceita(SimpleFactory.getRandomLong());
        request.setDia(now().minusDays(1));
        request.setPeriodo(JA);

        Alimento alimento = getBuilder();

        Receita receita = get();
        receita.setId(request.getIdReceita());
        receita.adicionarAlimento(alimento, "100");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarReceitaService.porId(request.getIdReceita())).thenReturn(receita);
        when(nowService.nowDate()).thenReturn(hoje);
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(false);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(true);

        adicionarAlimentoRefeicaoPorReceitaService.adicionar(request);

        verify(refeicaoRepository).save(refeicaoCaptor.capture());

        Refeicao refeicao = refeicaoCaptor.getValue();

        verify(atualizarRefeicaoService).atualizar(refeicao);

        assertEquals("0", usuario.getPontuacao());
    }

    @Test
    @DisplayName("Não deve dar pontos ao usuário se já adicionou refeição no dia")
    void naoDeveDarPontosAoUsuarioSeJaAdicionouRefeicaoNoDia(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();

        RefeicaoPorReceitaRequest request = new RefeicaoPorReceitaRequest();
        request.setIdReceita(SimpleFactory.getRandomLong());
        request.setDia(hoje);
        request.setPeriodo(JA);

        Alimento alimento = getBuilder();

        Receita receita = get();
        receita.setId(request.getIdReceita());
        receita.adicionarAlimento(alimento, "100");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarReceitaService.porId(request.getIdReceita())).thenReturn(receita);
        when(nowService.nowDate()).thenReturn(hoje);
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(true);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(true);

        adicionarAlimentoRefeicaoPorReceitaService.adicionar(request);

        verify(usuarioRepository, never()).save(any());
        verify(conquistasService, never()).pontos(any());

        assertEquals("0", usuario.getPontuacao());
    }

    @Test
    @DisplayName("Deve retornar um erro caso a receita não exista")
    void deveRetornarErroComReceitaInexistente(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();

        RefeicaoPorReceitaRequest request = new RefeicaoPorReceitaRequest();
        request.setIdReceita(SimpleFactory.getRandomLong());
        request.setDia(now());
        request.setPeriodo(JA);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarReceitaService.porId(request.getIdReceita())).thenThrow(ResponseStatusException.class);
        when(nowService.nowDate()).thenReturn(hoje);

        assertThrows(ResponseStatusException.class, () -> adicionarAlimentoRefeicaoPorReceitaService.adicionar(request));
    }

    @Test
    @DisplayName("Deve setar o usuário na refeição se esta for nova")
    void deveAdicionarUsuarioNaRefeicaoSeForNova(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();

        Receita receita = get();
        receita.adicionarAlimento(AlimentoFactory.getBuilder(), "100");


        RefeicaoPorReceitaRequest request = new RefeicaoPorReceitaRequest();
        request.setIdReceita(SimpleFactory.getRandomLong());
        request.setDia(now());
        request.setPeriodo(JA);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(nowService.nowDate()).thenReturn(hoje);
        when(buscarReceitaService.porId(request.getIdReceita())).thenReturn(receita);
        when(refeicaoRepository.findByPeriodoEqualsAndDiaEquals(request.getPeriodo(), request.getDia()))
                .thenReturn(ofNullable(toEntity(request)));
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(true);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(true);

        adicionarAlimentoRefeicaoPorReceitaService.adicionar(request);

        verify(refeicaoRepository).save(refeicaoCaptor.capture());

        Refeicao refeicaoNova = refeicaoCaptor.getValue();

        assertEquals(refeicaoNova.getUsuario(), usuario);
    }

    @Test
    @DisplayName("Deve adicionar um alimento a uma refeição existente corretamente")
    void deveAdicionarAlimentoARefeicaoExistente(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();

        Alimento alimento = AlimentoFactory.getBuilder();

        Receita receita = get();
        receita.adicionarAlimento(alimento, "100");

        RefeicaoPorReceitaRequest request = new RefeicaoPorReceitaRequest();
        request.setIdReceita(SimpleFactory.getRandomLong());
        request.setDia(now());
        request.setPeriodo(LM);

        Refeicao refeicao = RefeicaoFactory.getBuilder();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(nowService.nowDate()).thenReturn(hoje);
        when(buscarReceitaService.porId(request.getIdReceita())).thenReturn(receita);
        when(refeicaoRepository.findByPeriodoEqualsAndDiaEquals(request.getPeriodo(), request.getDia()))
                .thenReturn(ofNullable(refeicao));
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(true);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(true);

        adicionarAlimentoRefeicaoPorReceitaService.adicionar(request);

        verify(refeicaoRepository).save(refeicaoCaptor.capture());

        Refeicao refeicaoNova = refeicaoCaptor.getValue();

        verify(refeicaoAlimentoRepository).save(refeicaoNova.getAlimentos().get(0));
        verify(atualizarRefeicaoService).atualizar(refeicaoNova);

        assertEquals(alimento, refeicaoNova.getAlimentos().get(0).getAlimento());
        assertEquals(request.getPeriodo(), refeicaoNova.getPeriodo());
        assertEquals(request.getDia(), refeicaoNova.getDia());
    }
}
