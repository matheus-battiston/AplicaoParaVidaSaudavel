package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ReceitaPreferenciasRequest;
import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ReceitaAlimentoRepository;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.validator.ReceitaJaAvaliadaValidator;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExibirPrincipaisReceitasServiceTest {
    @InjectMocks
    private ExibirPrincipaisReceitasService tested;

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private ReceitaAlimentoRepository receitaAlimentoRepository;

    @Mock
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;

    @Test
    @DisplayName("Deve ter uma resposta vazia se nao tiver receitas")
    void deveTerListaVazia(){
        Pageable pageable = PageRequest.of(0,5);

        Page<Receita> respostaEsperada = new PageImpl<>(new ArrayList<>());

        ReceitaPreferenciasRequest request = new ReceitaPreferenciasRequest();

        List<String> alergias = new ArrayList<>();
        alergias.add("Ovo");

        request.setAlergias(alergias);

        ReceitaAlimento alimento = new ReceitaAlimento();
        alimento.setAlimento(AlimentoFactory.getBuilder());
        alimento.setQuantidade("100");

        List<ReceitaAlimento> alimentosAlergias = new ArrayList<>();
        alimentosAlergias.add(alimento);

        when(receitaAlimentoRepository.findByAlimentoNome(alergias.get(0))).thenReturn(alimentosAlergias);
        when(receitaRepository.findTopPorAlergias(alimentosAlergias, pageable)).thenReturn(respostaEsperada);

        Page<ReceitaResponse> response = tested.exibir(pageable, request);

        assertTrue(response.getContent().isEmpty());
    }

    @Test
    @DisplayName("Deve ter uma lista de receitas")
    void deveTerListaDeReceitas(){
        Pageable pageable = PageRequest.of(0,5);
        Receita receita = ReceitaFactory.get();
        receita.setCriador(UsuarioFactory.get());
        receita.adicionarAlimento(AlimentoFactory.getBuilder(), "100");

        ReceitaPreferenciasRequest request = new ReceitaPreferenciasRequest();

        List<String> alergias = new ArrayList<>();
        alergias.add("Ovo");
        request.setAlergias(alergias);

        ReceitaAlimento alimento = new ReceitaAlimento();
        alimento.setAlimento(AlimentoFactory.getBuilder());
        alimento.setQuantidade("100");

        List<ReceitaAlimento> alimentosAlergias = new ArrayList<>();
        alimentosAlergias.add(alimento);

        Page<Receita> respostaEsperada = new PageImpl<>(List.of(receita));

        when(receitaAlimentoRepository.findByAlimentoNome(alergias.get(0))).thenReturn(alimentosAlergias);
        when(receitaRepository.findTopPorAlergias(alimentosAlergias, pageable)).thenReturn(respostaEsperada);
        when(receitaJaAvaliadaValidator.validar(receita)).thenReturn(true);

        Page<ReceitaResponse> response = tested.exibir(pageable, request);

        assertEquals(1 ,response.getContent().size());
        assertEquals(receita.getId() ,response.getContent().get(0).getId());
    }
}
