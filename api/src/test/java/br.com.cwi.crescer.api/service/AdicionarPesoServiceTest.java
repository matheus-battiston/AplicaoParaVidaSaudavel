package br.com.cwi.crescer.api.service;


import br.com.cwi.crescer.api.domain.Peso;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.PesoRepository;
import br.com.cwi.crescer.api.security.controller.request.AdicionarPesoRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.AdicionarPesoService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static java.lang.Double.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdicionarPesoServiceTest {
    @InjectMocks
    private AdicionarPesoService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private PesoRepository pesoRepository;

    @Mock
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;


    @Captor
    private ArgumentCaptor<Peso> pesoArgumentCaptor;

    @Test
    @DisplayName("Deve adicionar um peso e alterar o atual")
    void adicionarPesoEAlterarAtual(){

        String valorEsperado = "70.0";
        Long valorPesoNovo = 70L;
        LocalDate dataNova = LocalDate.of(2022, 10, 20);
        Long valorPesoAntigo = 100L;
        LocalDate dataAntiga = LocalDate.of(2022, 10, 5);
        Usuario usuario = UsuarioFactory.get();
        Peso pesoAntigo = new Peso();
        pesoAntigo.setValor(valorPesoAntigo.toString());
        pesoAntigo.setUsuario(usuario);
        pesoAntigo.setDataRegistro(dataAntiga);
        usuario.adicionarPeso(pesoAntigo);
        usuario.setPeso(valorPesoAntigo.toString());

        AdicionarPesoRequest request = new AdicionarPesoRequest();
        request.setData(dataNova);
        request.setPeso(valueOf(valorPesoNovo));

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(pesoRepository.findFirstByUsuarioOrderByDataRegistroDesc(usuario)).thenReturn(pesoAntigo);

        tested.adicionar(request);

        verify(pesoRepository).save(pesoArgumentCaptor.capture());
        verify(calcularRecomendacoesUsuarioService).calcular();
        Peso pesoSalvo = pesoArgumentCaptor.getValue();

        assertEquals(valorEsperado, pesoSalvo.getValor());
        assertEquals(dataNova, pesoSalvo.getDataRegistro());
        assertEquals(valorEsperado, pesoSalvo.getUsuario().getPeso());
        assertEquals(2, pesoSalvo.getUsuario().getPesos().size());
    }


    @Test
    @DisplayName("Deve adicionar um peso e nao alterar o valor atual")
    void deveAdicionarPesoENaoAlterarAtual(){
        String valorPesoNovo = "70.0";
        LocalDate dataNova = LocalDate.of(2022, 10, 20);
        String valorPesoAntigo = "100.0";
        LocalDate dataAntiga = LocalDate.of(2022, 10, 5);
        Usuario usuario = UsuarioFactory.get();
        Peso pesoAntigo = new Peso();
        pesoAntigo.setValor(valorPesoAntigo);
        pesoAntigo.setUsuario(usuario);
        pesoAntigo.setDataRegistro(dataNova);

        usuario.adicionarPeso(pesoAntigo);
        usuario.setPeso(valorPesoAntigo);

        AdicionarPesoRequest request = new AdicionarPesoRequest();
        request.setData(dataAntiga);
        request.setPeso(valueOf(valorPesoNovo));


        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(pesoRepository.findFirstByUsuarioOrderByDataRegistroDesc(usuario)).thenReturn(pesoAntigo);

        tested.adicionar(request);

        verify(pesoRepository).save(pesoArgumentCaptor.capture());
        verify(calcularRecomendacoesUsuarioService).calcular();
        Peso pesoSalvo = pesoArgumentCaptor.getValue();

        assertEquals(valorPesoNovo, pesoSalvo.getValor());
        assertEquals(dataAntiga, pesoSalvo.getDataRegistro());
        assertEquals(valorPesoAntigo, pesoSalvo.getUsuario().getPeso());
        assertEquals(2, pesoSalvo.getUsuario().getPesos().size());
    }
}
