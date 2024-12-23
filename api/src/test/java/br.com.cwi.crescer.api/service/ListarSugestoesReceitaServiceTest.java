package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.validator.ReceitaJaAvaliadaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarSugestoesReceitaServiceTest {
    @InjectMocks
    private ListarSugestoesReceitaService listarSugestoesReceitaService;

    @Mock
    private BuscarReceitaService buscarReceitaService;

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;

    @Test
    @DisplayName("Deve listar sugestões de receitas semelhantes corretamente")
    void deveListarSugestoesReceitas(){
        Receita receita = ReceitaFactory.get();
        receita.setCalorias(100);
        receita.setProteinas(5);
        receita.setCriador(UsuarioFactory.get());

        List<Receita> receitasSemelhantes = new ArrayList<>();

        Receita receita2 = ReceitaFactory.get();
        receita2.setCalorias(99);
        receita2.setProteinas(4);
        receita2.setCriador(UsuarioFactory.get());

        receitasSemelhantes.add(receita2);

        when(buscarReceitaService.porId(receita.getId())).thenReturn(receita);
        when(receitaRepository
                .findSugestions(receita.getCalorias().doubleValue()
                        , receita.getProteinas().doubleValue()
                        , receita.getId())).thenReturn(receitasSemelhantes);
        when(receitaJaAvaliadaValidator.validar(receita2)).thenReturn(true);

        List<ReceitaResponse> response = listarSugestoesReceitaService.listar(receita.getId());

        assertEquals(receita2.getId(), response.get(0).getId());
    }

}
