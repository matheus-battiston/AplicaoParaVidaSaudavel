package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.repository.AlimentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ListarAlimentosServiceTest {
    @InjectMocks
    private ListarAlimentosService listarAlimentosService;
    @Mock
    private AlimentoRepository alimentoRepository;

    @Test
    @DisplayName("Deve buscar todos os alimetnos e retornar reponse paginado")
    void deveRetornarTodosUsuarios() {
        Pageable pageable = PageRequest.of(0, 5);

        List<Alimento> alimentos = List.of(AlimentoFactory.getBuilder(), AlimentoFactory.getBuilder(), AlimentoFactory.getBuilder());
        Page<Alimento> alimentosPaginados = new PageImpl<>(alimentos);

        when(this.alimentoRepository.findByNomeContainsIgnoreCase("", pageable)).thenReturn(alimentosPaginados);

        Page<AlimentoResponse> response = this.listarAlimentosService.listarPaginado("",pageable);

        verify(this.alimentoRepository).findByNomeContainsIgnoreCase("",pageable);

        Assertions.assertEquals(alimentos.size(), response.getSize());
        Assertions.assertEquals((alimentos.get(0)).getId(), (response.getContent().get(0)).getId());
        Assertions.assertEquals((alimentos.get(1)).getId(), (response.getContent().get(1)).getId());
        Assertions.assertEquals((alimentos.get(2)).getId(), (response.getContent().get(2)).getId());
    }
    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar alimentos")
    void deveRetornarListaVazia() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<Alimento> alimentosPaginado = new PageImpl<>(new ArrayList());

        when(this.alimentoRepository.findByNomeContainsIgnoreCase("", pageable)).thenReturn(alimentosPaginado);
        Page<AlimentoResponse> response = this.listarAlimentosService.listarPaginado("",pageable);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getSize());
    }
}
