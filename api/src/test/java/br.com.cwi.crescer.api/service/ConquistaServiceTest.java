package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.*;
import br.com.cwi.crescer.api.factories.ConquistaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.AguaRepository;
import br.com.cwi.crescer.api.repository.ConquistaUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConquistaServiceTest {
    @InjectMocks
    private ConquistasService tested;
    @Mock
    private NowService nowService;
    @Mock
    private ConquistaUsuarioRepository conquistaUsuarioRepository;

    @Mock
    private AguaRepository aguaRepository;

    @Test
    @DisplayName("Deve conceder a conquista de todas as conquistas quando usuario tiver conquistado")
    void deveConcederTodasAsConquistas(){
        LocalDate hoje = LocalDate.now();
        Long conquistasObtidas = 19L;
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();
        conquista.setCategoria(Categoria.PLATINA);
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setDesbloqueada(false);
        conquistaUsuario.setConquista(conquista);
        conquistaUsuario.setUsuario(usuario);

        when(conquistaUsuarioRepository.countDistinctByUsuarioAndDesbloqueadaIsTrue(usuario)).thenReturn(conquistasObtidas);
        when(conquistaUsuarioRepository.findFirstByUsuarioAndConquistaTipo(usuario, TipoConquista.CONQUISTA)).thenReturn(conquistaUsuario);
        when(nowService.nowDate()).thenReturn(hoje);

        tested.checarTodasConquistas(usuario);

        assertEquals(ConquistasService.PONTOS_CONQUISTA_PLATINA, Long.valueOf(usuario.getPontuacao()));
        assertTrue(conquistaUsuario.isDesbloqueada());
        assertEquals(hoje, conquistaUsuario.getDataAlteracao());
    }
    @Test
    @DisplayName("Deve alterar conquista para desbloqueado quando ja cumpriu objetivo e somar os pontos para conquista bronze")
    void deveAlterarConquistaParaDesbloqueado(){
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();
        ConquistaUsuario conquistaUsuario = ConquistaUsuario.builder().build();
        conquistaUsuario.setConquista(conquista);
        conquistaUsuario.setProgresso(conquista.getValor() + 1);
        conquistaUsuario.setUsuario(usuario);

        tested.checarConquista(conquistaUsuario);

        assertTrue(conquistaUsuario.isDesbloqueada());
        assertEquals(ConquistasService.PONTOS_CONQUISTA_BRONZE, valueOf(usuario.getPontuacao()));
    }

    @Test
    @DisplayName("Deve alterar conquista para desbloqueado quando ja cumpriu objetivo e somar os pontos para conquista prata")
    void deveAlterarConquistaParaDesbloqueadoESomarPontosPrata(){
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();
        conquista.setCategoria(Categoria.PRATA);
        ConquistaUsuario conquistaUsuario = ConquistaUsuario.builder().build();
        conquistaUsuario.setConquista(conquista);
        conquistaUsuario.setProgresso(conquista.getValor() + 1);
        conquistaUsuario.setUsuario(usuario);

        tested.checarConquista(conquistaUsuario);

        assertTrue(conquistaUsuario.isDesbloqueada());
        assertEquals(ConquistasService.PONTOS_CONQUISTA_PRATA, valueOf(usuario.getPontuacao()));
    }

    @Test
    @DisplayName("Deve alterar conquista para desbloqueado quando ja cumpriu objetivo e somar os pontos para conquista ouro")
    void deveAlterarConquistaParaDesbloqueadoESomarPontosOuro(){
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();
        conquista.setCategoria(Categoria.OURO);

        ConquistaUsuario conquistaUsuario = ConquistaUsuario.builder().build();
        conquistaUsuario.setConquista(conquista);
        conquistaUsuario.setProgresso(conquista.getValor() + 1);
        conquistaUsuario.setUsuario(usuario);

        tested.checarConquista(conquistaUsuario);

        assertTrue(conquistaUsuario.isDesbloqueada());
        assertEquals(ConquistasService.PONTOS_CONQUISTA_OURO, valueOf(usuario.getPontuacao()));
    }

    @Test
    @DisplayName("Deve alterar conquista para desbloqueado quando ja cumpriu objetivo e somar os pontos para conquista platina")
    void deveAlterarConquistaParaDesbloqueadoESomarPontosPlatina(){
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();
        conquista.setCategoria(Categoria.PLATINA);

        ConquistaUsuario conquistaUsuario = ConquistaUsuario.builder().build();
        conquistaUsuario.setConquista(conquista);
        conquistaUsuario.setProgresso(conquista.getValor() + 1);
        conquistaUsuario.setUsuario(usuario);

        tested.checarConquista(conquistaUsuario);

        assertTrue(conquistaUsuario.isDesbloqueada());
        assertEquals(ConquistasService.PONTOS_CONQUISTA_PLATINA, valueOf(usuario.getPontuacao()));
    }

    @Test
    @DisplayName("Deve realizar o progresso quando teve atualizaçao no ultimo dia")
    void deveRealizarProgresso(){
        long progresso = 2L;
        Conquista conquista = ConquistaFactory.get();
        ConquistaUsuario conquistaUsuario = ConquistaUsuario.builder().build();
        conquistaUsuario.setConquista(conquista);
        conquistaUsuario.setDataAlteracao(LocalDate.now().minusDays(1));
        conquistaUsuario.setProgresso(progresso);

        when(nowService.nowDate()).thenReturn(LocalDate.now());

        tested.progressoStreakDias(conquistaUsuario);

        assertEquals(progresso + 1L, conquistaUsuario.getProgresso());
    }

    @Test
    @DisplayName("Deve resetar o progresso quando nao teve atualizaçao no ultimo dia")
    void deveResetarProgresso(){
        long progresso = 10L;
        long progressoResetado = 1L;
        Conquista conquista = ConquistaFactory.get();
        ConquistaUsuario conquistaUsuario = ConquistaUsuario.builder().build();
        conquistaUsuario.setConquista(conquista);
        conquistaUsuario.setProgresso(progresso);

        tested.progressoStreakDias(conquistaUsuario);

        assertEquals(progressoResetado, conquistaUsuario.getProgresso());
    }

    @Test
    @DisplayName("Nao deve fazer nada caso a agua consumida nao seja suficiente")
    void naoFazerNadaPorNaoSerSuficiente(){
        Usuario usuario = UsuarioFactory.get();
        usuario.setAguaRecomendacao(500);
        LocalDate hoje = nowService.nowDate();
        Conquista conquista = ConquistaFactory.get();
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setUsuario(usuario);
        usuario.getConquistas().add(conquistaUsuario);
        Agua aguaUsuario = new Agua();
        aguaUsuario.setDataRegistro(hoje);
        aguaUsuario.setQuantidade(10);

        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), hoje)).thenReturn(Optional.of(aguaUsuario));

        tested.agua(usuario);

        verify(conquistaUsuarioRepository, never()).findAllByUsuarioAndConquistaTipo(any(), any());
    }

    @Test
    @DisplayName("Deve fazer progressao do consumo de agua")
    void deveFazerProgressaoDoConsumoDeAgua(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = nowService.nowDate();
        usuario.setAguaRecomendacao(500);
        Conquista conquista = ConquistaFactory.get();
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setConquista(conquista);
        conquistaUsuario.setUsuario(usuario);
        usuario.getConquistas().add(conquistaUsuario);
        Agua aguaUsuario = new Agua();
        aguaUsuario.setDataRegistro(hoje);
        aguaUsuario.setQuantidade(700);


        List<ConquistaUsuario> conquistas = List.of(conquistaUsuario);

        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), hoje)).thenReturn(Optional.of(aguaUsuario));
        when(conquistaUsuarioRepository.findAllByUsuarioAndConquistaTipo(usuario, conquista.getTipo())).thenReturn(conquistas);

        tested.agua(usuario);

        verify(conquistaUsuarioRepository).findAllByUsuarioAndConquistaTipo(usuario, conquista.getTipo());
    }

    @Test
    @DisplayName("Deve fazer a progressao de uma conquista em quantidade bruta")
    void deveFazerProgressaoPost(){
        Conquista conquista = ConquistaFactory.get();
        long progresso = 10L;
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setProgresso(progresso);
        conquistaUsuario.setConquista(conquista);

        tested.progressoQuantidadeBruta(conquistaUsuario);

        assertEquals(progresso + 1, conquistaUsuario.getProgresso());
    }

    @Test
    @DisplayName("Deve fazer a progressao da conquista de pontos")
    void deveProgredirConquistaPontos(){
        Usuario usuario = UsuarioFactory.get();
        String pontuacao = "100";
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setUsuario(usuario);
        Conquista conquista = ConquistaFactory.get();
        conquista.setValor(100L);
        conquistaUsuario.setConquista(conquista);

        tested.progressoPontos(pontuacao, conquistaUsuario);

        assertEquals(Long.valueOf(pontuacao), conquistaUsuario.getProgresso());
    }

    @Test
    @DisplayName("Deve fazer progressao de uma conquista por quantidade de acoes")
    void deveProgredirConquistaQuantidadeAcoes(){
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();
        ConquistaUsuario conquistaUsuario = new ConquistaUsuario();
        conquistaUsuario.setConquista(conquista);
        conquistaUsuario.setProgresso(0L);

        when(conquistaUsuarioRepository.findAllByUsuarioAndConquistaTipo(usuario, conquista.getTipo())).thenReturn(List.of(conquistaUsuario));

        tested.conquistasQuantidadeDeAcoes(usuario, conquista.getTipo());

        verify(conquistaUsuarioRepository).findAllByUsuarioAndConquistaTipo(usuario, conquista.getTipo());

        assertEquals(1L, conquistaUsuario.getProgresso());
    }
}
