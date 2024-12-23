package br.com.cwi.crescer.api.service;


import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
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

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExibirReceitasSeguindoServiceTest {
    @InjectMocks
    private ExibirReceitasSeguindoService tested;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nao seguir ninguem")
    void deveRetornarListaVazia(){
        Pageable pageable = PageRequest.of(0,5);
        Usuario usuario = UsuarioFactory.get();
        Page<Receita> postPaginados = new PageImpl<>(new ArrayList<>());

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(receitaRepository.findReceitasSeguindo(usuario.getSeguindo(), usuario, pageable)).thenReturn(postPaginados);
        Page<ReceitaResponse> response = tested.exibir(pageable);

        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar os receitas de usuarios seguidos")
    void deveRetornarReceitasDeUsuariosSeguidos(){
        Pageable pageable = PageRequest.of(0,5);
        Usuario usuario = UsuarioFactory.get();
        List<Receita> receitas = of(ReceitaFactory.get(), ReceitaFactory.get());
        receitas.get(0).setCriador(UsuarioFactory.get());
        receitas.get(1).setCriador(UsuarioFactory.get());
        receitas.get(0).adicionarAlimento(AlimentoFactory.getBuilder(), "100");
        receitas.get(1).adicionarAlimento(AlimentoFactory.getBuilder(), "100");
        Page<Receita> receitasPaginadas = new PageImpl<>(receitas);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(receitaRepository.findReceitasSeguindo(usuario.getSeguindo(), usuario, pageable)).thenReturn(receitasPaginadas);
        when(receitaJaAvaliadaValidator.validar(receitas.get(0))).thenReturn(false);

        Page<ReceitaResponse> response = tested.exibir(pageable);

        assertEquals(2, response.getSize());
        assertEquals(receitas.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(receitas.get(1).getId(), response.getContent().get(1).getId());

    }
}
