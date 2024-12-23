package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AguaRequest;
import br.com.cwi.crescer.api.domain.Agua;
import br.com.cwi.crescer.api.repository.AguaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.ConsumirAguaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static br.com.cwi.crescer.api.factories.AguaFactory.getRequest;
import static br.com.cwi.crescer.api.factories.UsuarioFactory.get;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsumirAguaServiceTest {

    @InjectMocks
    private ConsumirAguaService consumirAguaService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private NowService nowService;

    @Mock
    private AguaRepository aguaRepository;

    @Mock
    private ConsumirAguaValidator consumirAguaValidator;

    @Mock
    private ConquistasService conquistasService;

    @Captor
    private ArgumentCaptor<Agua> aguaArgumentCaptor;

    @Test
    @DisplayName("Deve adicionar registro de água consumida corretamente")
    void deveAdicionarRegistroAguaConsumida(){
        Usuario usuario = get();
        usuario.setAguaRecomendacao(2000);
        AguaRequest request = getRequest();
        Optional<Agua> aguaZerada = Optional.of(new Agua());
        aguaZerada.get().setQuantidade(0);
        aguaZerada.get().setDataRegistro(LocalDate.now());
        LocalDate hoje = LocalDate.now();

        when(nowService.nowDate()).thenReturn(hoje);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData())).thenReturn(aguaZerada);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData().minusDays(1))).thenReturn(aguaZerada);

        consumirAguaService.consumir(request);

        verify(usuarioAutenticadoService).get();
        verify(conquistasService).agua(usuario);
        verify(aguaRepository).save(aguaArgumentCaptor.capture());

        Agua aguaUsuario = aguaArgumentCaptor.getValue();

        assertEquals(request.getQuantidade(), aguaUsuario.getQuantidade());
        assertEquals(request.getData(), aguaUsuario.getDataRegistro());
    }

    @Test
    @DisplayName("Deve adicionar pontos ao usuário bater a meta diária de água (sem streak)")
    void deveAdicionarPontosAoBaterMetaDiariaSemStreak(){
        Usuario usuario = get();
        usuario.setAguaRecomendacao(2000);
        int pontosAntesDeAdicionar = parseInt(usuario.getPontuacao());
        AguaRequest request = getRequest();
        Optional<Agua> aguaZerada = Optional.of(new Agua());
        aguaZerada.get().setQuantidade(0);
        aguaZerada.get().setDataRegistro(LocalDate.now());

        LocalDate hoje = LocalDate.now();

        when(nowService.nowDate()).thenReturn(hoje);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData())).thenReturn(aguaZerada);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData().minusDays(1))).thenReturn(aguaZerada);

        consumirAguaService.consumir(request);

        verify(usuarioAutenticadoService).get();

        assertEquals(20, parseInt(usuario.getPontuacao()));
        assertEquals(0, pontosAntesDeAdicionar);
    }

    @Test
    @DisplayName("Deve adicionar pontos ao usuário bater a meta diária de água (com streak)")
    void deveAdicionarPontosAoBaterMetaDiariaComStreak(){
        Usuario usuario = get();
        usuario.setAguaRecomendacao(2000);
        int pontosAntesDeAdicionar = parseInt(usuario.getPontuacao());
        AguaRequest request = getRequest();

        Optional<Agua> aguaZerada = Optional.of(new Agua());
        aguaZerada.get().setQuantidade(0);
        aguaZerada.get().setDataRegistro(LocalDate.now());

        Optional<Agua> aguaMetaBatida = Optional.of(new Agua());
        aguaMetaBatida.get().setQuantidade(2000);
        aguaMetaBatida.get().setDataRegistro(LocalDate.now().minusDays(1));

        LocalDate hoje = LocalDate.now();

        when(nowService.nowDate()).thenReturn(hoje);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData())).thenReturn(aguaZerada);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData().minusDays(1))).thenReturn(aguaMetaBatida);

        consumirAguaService.consumir(request);

        verify(usuarioAutenticadoService).get();

        assertEquals(40, parseInt(usuario.getPontuacao()));
        assertEquals(0, pontosAntesDeAdicionar);
    }

    @Test
    @DisplayName("Não deve contar progresso para a conquista se água adicionada em um dia passado")
    void naoDeveContarProgressoConquistaSeAguaAdicionadaDiaPassado(){
        Usuario usuario = get();
        usuario.setAguaRecomendacao(2000);

        AguaRequest request = getRequest();
        request.setData(LocalDate.now().minusDays(1));

        Optional<Agua> aguaZerada = Optional.of(new Agua());
        aguaZerada.get().setQuantidade(2000);
        aguaZerada.get().setDataRegistro(LocalDate.now().minusDays(1));


        LocalDate hoje = LocalDate.now();

        when(nowService.nowDate()).thenReturn(hoje);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData())).thenReturn(aguaZerada);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData().minusDays(1))).thenReturn(aguaZerada);

        consumirAguaService.consumir(request);

        verify(usuarioAutenticadoService).get();
        verify(conquistasService, never()).agua(any());
    }

    @Test
    @DisplayName("Não deve adicionar pontos se água adicionada em um dia passado")
    void naoDeveAdicionarPontosSeAguaAdicionadaDiaPassado(){
        Usuario usuario = get();
        usuario.setAguaRecomendacao(2000);

        AguaRequest request = getRequest();
        request.setQuantidade(2000);
        request.setData(LocalDate.now().minusDays(1));

        Optional<Agua> aguaZerada = Optional.of(new Agua());
        aguaZerada.get().setQuantidade(0);
        aguaZerada.get().setDataRegistro(LocalDate.now().minusDays(1));

        LocalDate hoje = LocalDate.now();

        when(nowService.nowDate()).thenReturn(hoje);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData())).thenReturn(aguaZerada);
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData().minusDays(1))).thenReturn(aguaZerada);

        consumirAguaService.consumir(request);

        verify(usuarioAutenticadoService).get();

        assertEquals("0", usuario.getPontuacao());
    }


}
