package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ReceitaResponse;
import br.com.cwi.crescer.api.domain.Alimento;
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
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ListarReceitasPessoaisServiceTest {
    @InjectMocks
    private ListarReceitasPessoaisService tested;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private CalcularAlimentoService calcularAlimentoService;
    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;

    @Test
    @DisplayName("Deve fazer uma lista de receitas do usuario")
    void deveListarReceitasDoUsuario(){
        Usuario usuario = UsuarioFactory.get();
        Receita receitaUm = ReceitaFactory.get();
        Receita receitaDois = ReceitaFactory.get();
        receitaUm.setCriador(usuario);
        receitaDois.setCriador(usuario);
        usuario.getReceitas().add(receitaUm);
        usuario.getReceitas().add(receitaDois);
        Pageable pageable = Pageable.ofSize(10);
        Page<Receita> receitas = new PageImpl<>(List.of(receitaUm, receitaDois));


        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(receitaRepository.findByUsuario_Id(usuario.getId(), pageable)).thenReturn(receitas);
        when(receitaJaAvaliadaValidator.validar(receitaUm)).thenReturn(false);

        Page<ReceitaResponse> response = tested.listar(pageable);

        assertEquals(2, response.getSize());
        assertEquals(receitaUm.getId(), response.getContent().get(0).getId());
        assertEquals(receitaDois.getId(), response.getContent().get(1).getId());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia se nao tem receitas pessoais")
    void deveRetornarListaVazia(){
        Usuario usuario = UsuarioFactory.get();
        Pageable pageable = Pageable.ofSize(10);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(receitaRepository.findByUsuario_Id(usuario.getId(), pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        Page<ReceitaResponse> response = tested.listar(pageable);

        assertTrue(response.getContent().isEmpty());
    }

    @Test
    @DisplayName("Deve mapear a receita para um response e lidar com os alimentos")
    void mapearParaResponseELidarComAlimentos(){
        Receita receita = ReceitaFactory.get();
        receita.setCriador(UsuarioFactory.get());
        Alimento alimento = AlimentoFactory.getBuilder();
        String quantidade = "100";
        receita.adicionarAlimento(alimento, quantidade);

        when(receitaJaAvaliadaValidator.validar(receita)).thenReturn(false);

        ReceitaResponse response = tested.transformar(receita);

        assertEquals(receita.getId(), response.getId());
        assertEquals(1, response.getAlimentos().size());
        assertEquals(receita.getDataCriacao(), response.getDataCriacao());
        assertEquals(receita.getUsuario().getId(), response.getUsuarioId());

    }
}
