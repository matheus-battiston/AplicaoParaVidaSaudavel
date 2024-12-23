package br.com.cwi.crescer.api.service;


import br.com.cwi.crescer.api.controller.response.PesoResponse;
import br.com.cwi.crescer.api.domain.Peso;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.PesoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.HistoricoPesoService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoricoPesoServiceTest {
    @InjectMocks
    private HistoricoPesoService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private PesoRepository pesoRepository;

    @Test
    @DisplayName("Deve retornar uma lista de pesos com a data")
    void deveRetornarORelatorio(){
        String valorUm = "100";
        String valorDois = "80";
        Usuario usuario = UsuarioFactory.get();
        LocalDate diaUm = LocalDate.of(2022, 10, 1);
        LocalDate diaDois = LocalDate.of(2022, 10, 2);
        Peso pesoUm = new Peso();
        pesoUm.setValor(valorUm);
        pesoUm.setDataRegistro(diaUm);
        Peso pesoDois = new Peso();
        pesoDois.setValor(valorDois);
        pesoDois.setDataRegistro(diaDois);
        usuario.getPesos().add(pesoUm);
        usuario.getPesos().add(pesoDois);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(pesoRepository.findAllByUsuarioOrderByDataRegistroDesc(usuario)).thenReturn(List.of(pesoUm, pesoDois));

        List<PesoResponse> responseList = tested.gerar();

        assertEquals(2, responseList.size());
        assertEquals(valorUm, responseList.get(0).getPeso());
        assertEquals(valorDois, responseList.get(1).getPeso());
        assertEquals(diaUm, responseList.get(0).getData());
        assertEquals(diaDois, responseList.get(1).getData());

    }

    @Test
    @DisplayName("Deve retornar lista vazia")
    void deveRetornarListaVazia(){
        Usuario usuario = UsuarioFactory.get();
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(pesoRepository.findAllByUsuarioOrderByDataRegistroDesc(usuario)).thenReturn(new ArrayList<>());


        List<PesoResponse> responseList = tested.gerar();

        assertTrue(responseList.isEmpty());
    }
}
