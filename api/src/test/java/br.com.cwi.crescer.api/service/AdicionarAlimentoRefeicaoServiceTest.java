package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.RefeicaoAlimentoRequest;
import br.com.cwi.crescer.api.controller.request.RefeicaoPorReceitaRequest;
import br.com.cwi.crescer.api.controller.request.RefeicaoRequest;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.RefeicaoFactory;
import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.mapper.RefeicaoMapper;
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
import static br.com.cwi.crescer.api.factories.RefeicaoFactory.getRequest;
import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static br.com.cwi.crescer.api.mapper.RefeicaoMapper.toEntity;
import static java.time.LocalDate.now;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class AdicionarAlimentoRefeicaoServiceTest {
    @InjectMocks
    private AdicionarAlimentoRefeicaoService adicionarAlimentoRefeicaoService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private BuscarAlimentoService buscarAlimentoService;

    @Mock
    private RefeicaoRepository refeicaoRepository;

    @Mock
    private RefeicaoAlimentoRepository refeicaoAlimentoRepository;

    @Mock
    private AtualizarRefeicaoService atualizarRefeicaoService;

    @Mock
    private NowService nowService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ConquistasService conquistasService;

    @Captor
    private ArgumentCaptor<Refeicao> refeicaoCaptor;

    @Test
    @DisplayName("Deve adicionar um alimento a uma refeicao corretamente")
    void deveAdicionarAlimentoRefeicao(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();
        RefeicaoAlimentoRequest refeicaoAlimento = new RefeicaoAlimentoRequest();
        refeicaoAlimento.setId(1L);
        refeicaoAlimento.setQuantidade("100");

        Alimento alimento = getBuilder();
        alimento.setId(1L);

        RefeicaoRequest request = getRequest();
        request.setAlimento(refeicaoAlimento);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarAlimentoService.porId(refeicaoAlimento.getId())).thenReturn(alimento);
        when(nowService.nowDate()).thenReturn(hoje);

        adicionarAlimentoRefeicaoService.adicionar(request);

        verify(refeicaoRepository).save(refeicaoCaptor.capture());

        Refeicao refeicao = refeicaoCaptor.getValue();

        verify(refeicaoAlimentoRepository).save(refeicao.getAlimentos().get(0));
        verify(atualizarRefeicaoService).atualizar(refeicao);

        assertEquals(alimento, refeicao.getAlimentos().get(0).getAlimento());
        assertEquals(request.getPeriodo(), refeicao.getPeriodo());
        assertEquals(request.getDia(), refeicao.getDia());
    }

    @Test
    @DisplayName("Deve dar pontos ao usuário quando adicionar alimento pela primeira vez no dia (sem streak)")
    void deveDarPontosAoUsuarioAoAdicionarAlimentoPelaPrimeiraVezSemStreak(){
        Usuario usuario = UsuarioFactory.get();
        RefeicaoAlimentoRequest refeicaoAlimento = new RefeicaoAlimentoRequest();
        LocalDate hoje = now();

        refeicaoAlimento.setId(1L);
        refeicaoAlimento.setQuantidade("100");

        Alimento alimento = getBuilder();
        alimento.setId(1L);

        RefeicaoRequest request = getRequest();
        request.setAlimento(refeicaoAlimento);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarAlimentoService.porId(refeicaoAlimento.getId())).thenReturn(alimento);
        when(nowService.nowDate()).thenReturn(hoje);

        adicionarAlimentoRefeicaoService.adicionar(request);

        verify(usuarioRepository).save(usuario);
        verify(conquistasService).pontos(usuario);

        assertEquals("20", usuario.getPontuacao());
    }

    @Test
    @DisplayName("Deve dar pontos ao usuário quando adicionar alimento pela primeira vez no dia (com streak)")
    void deveDarPontosAoUsuarioAoAdicionarAlimentoPelaPrimeiraVezComStreak(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();
        RefeicaoAlimentoRequest refeicaoAlimento = new RefeicaoAlimentoRequest();
        Refeicao refeicao = RefeicaoFactory.getBuilder();

        refeicao.setDia(now().minusDays(1));
        usuario.adicionarRefeicao(refeicao);

        refeicaoAlimento.setId(1L);
        refeicaoAlimento.setQuantidade("100");

        Alimento alimento = getBuilder();
        alimento.setId(1L);

        RefeicaoRequest request = getRequest();
        request.setAlimento(refeicaoAlimento);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarAlimentoService.porId(refeicaoAlimento.getId())).thenReturn(alimento);
        when(nowService.nowDate()).thenReturn(hoje);
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(false);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(true);

        adicionarAlimentoRefeicaoService.adicionar(request);
        verify(conquistasService).pontos(usuario);


        assertEquals("40", usuario.getPontuacao());
    }

    @Test
    @DisplayName("Não deve dar pontos ao usuário quando adicionar alimento por receita em um dia passado")
    void naoDeveDarPontosAoUsuarioAoAdicionarAlimentoEmDiaPassado() {
        Usuario usuario = UsuarioFactory.get();
        RefeicaoAlimentoRequest refeicaoAlimento = new RefeicaoAlimentoRequest();
        LocalDate hoje = now();

        refeicaoAlimento.setId(1L);
        refeicaoAlimento.setQuantidade("100");

        Alimento alimento = getBuilder();
        alimento.setId(1L);

        RefeicaoRequest request = getRequest();
        request.setAlimento(refeicaoAlimento);
        request.setDia(now().minusDays(1));

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarAlimentoService.porId(refeicaoAlimento.getId())).thenReturn(alimento);
        when(nowService.nowDate()).thenReturn(hoje);

        adicionarAlimentoRefeicaoService.adicionar(request);

        verify(conquistasService, never()).pontos(any());
        assertEquals("0", usuario.getPontuacao());
    }

    @Test
    @DisplayName("Não deve adicionar um alimento a uma refeicao se o alimento não existe")
    void naoDeveAdicionarAlimentosInexistenteRefeicao(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();

        RefeicaoAlimentoRequest refeicaoAlimento = new RefeicaoAlimentoRequest();
        refeicaoAlimento.setId(getRandomLong());
        refeicaoAlimento.setQuantidade("100");

        RefeicaoRequest request = getRequest();
        request.setAlimento(refeicaoAlimento);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarAlimentoService.porId(refeicaoAlimento.getId())).thenThrow(ResponseStatusException.class);
        when(nowService.nowDate()).thenReturn(hoje);

        assertThrows(ResponseStatusException.class, () -> adicionarAlimentoRefeicaoService.adicionar(request));

        verify(conquistasService, never()).pontos(any());

        }

    @Test
    @DisplayName("Não deve dar pontos ao usuário se já adicionou refeição no dia")
    void naoDeveDarPontosAoUsuarioSeJaAdicionouRefeicaoNoDia(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();
        Alimento alimento = AlimentoFactory.getBuilder();

        RefeicaoAlimentoRequest refeicaoAlimentoRequest = new RefeicaoAlimentoRequest();
        refeicaoAlimentoRequest.setQuantidade("100");
        refeicaoAlimentoRequest.setId(alimento.getId());

        RefeicaoRequest request = new RefeicaoRequest();
        request.setAlimento(refeicaoAlimentoRequest);
        request.setDia(hoje);
        request.setPeriodo(JA);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(nowService.nowDate()).thenReturn(hoje);
        when(refeicaoRepository.findByPeriodoEqualsAndDiaEquals(request.getPeriodo(), request.getDia()))
                .thenReturn(ofNullable(toEntity(request)));
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(true);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(true);
        when(buscarAlimentoService.porId(alimento.getId())).thenReturn(alimento);

        adicionarAlimentoRefeicaoService.adicionar(request);

        verify(usuarioRepository, never()).save(any());
        verify(conquistasService, never()).pontos(any());

        assertEquals("0", usuario.getPontuacao());
    }

    @Test
    @DisplayName("Deve setar o usuário na refeição se esta for nova")
    void deveAdicionarUsuarioNaRefeicaoSeForNova(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();
        Alimento alimento = AlimentoFactory.getBuilder();

        RefeicaoAlimentoRequest refeicaoAlimentoRequest = new RefeicaoAlimentoRequest();
        refeicaoAlimentoRequest.setQuantidade("100");
        refeicaoAlimentoRequest.setId(alimento.getId());

        RefeicaoRequest request = new RefeicaoRequest();
        request.setAlimento(refeicaoAlimentoRequest);
        request.setDia(hoje);
        request.setPeriodo(JA);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(nowService.nowDate()).thenReturn(hoje);
        when(buscarAlimentoService.porId(alimento.getId())).thenReturn(alimento);
        when(refeicaoRepository.findByPeriodoEqualsAndDiaEquals(request.getPeriodo(), request.getDia()))
                .thenReturn(ofNullable(RefeicaoMapper.toEntity(request)));
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(true);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(true);

        adicionarAlimentoRefeicaoService.adicionar(request);

        verify(refeicaoRepository).save(refeicaoCaptor.capture());

        Refeicao refeicaoNova = refeicaoCaptor.getValue();

        assertEquals(refeicaoNova.getUsuario(), usuario);
    }

    @Test
    @DisplayName("Deve adicionar um alimento a uma refeição existente corretamente")
    void deveAdicionarAlimentoEmRefeicaoExistente(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = now();
        Alimento alimento = AlimentoFactory.getBuilder();

        RefeicaoAlimentoRequest refeicaoAlimentoRequest = new RefeicaoAlimentoRequest();
        refeicaoAlimentoRequest.setQuantidade("100");
        refeicaoAlimentoRequest.setId(alimento.getId());

        RefeicaoRequest request = new RefeicaoRequest();
        request.setAlimento(refeicaoAlimentoRequest);
        request.setDia(hoje);
        request.setPeriodo(LM);

        Refeicao refeicao = RefeicaoFactory.getBuilder();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(nowService.nowDate()).thenReturn(hoje);
        when(buscarAlimentoService.porId(alimento.getId())).thenReturn(alimento);
        when(refeicaoRepository.findByPeriodoEqualsAndDiaEquals(request.getPeriodo(), request.getDia()))
                .thenReturn(ofNullable(refeicao));
        when(refeicaoRepository.existsByDia(hoje)).thenReturn(true);
        when(refeicaoRepository.existsByDia(hoje.minusDays(1))).thenReturn(true);

        adicionarAlimentoRefeicaoService.adicionar(request);

        verify(refeicaoRepository).save(refeicaoCaptor.capture());

        Refeicao refeicaoAtualizada = refeicaoCaptor.getValue();

        verify(refeicaoAlimentoRepository).save(refeicaoAtualizada.getAlimentos().get(0));
        verify(atualizarRefeicaoService).atualizar(refeicaoAtualizada);

        assertEquals(alimento, refeicaoAtualizada.getAlimentos().get(0).getAlimento());
        assertEquals(request.getPeriodo(), refeicaoAtualizada.getPeriodo());
        assertEquals(request.getDia(), refeicaoAtualizada.getDia());
    }

}
