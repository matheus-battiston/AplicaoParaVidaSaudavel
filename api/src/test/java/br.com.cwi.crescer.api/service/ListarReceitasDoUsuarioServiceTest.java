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
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarReceitasDoUsuarioServiceTest {
    @InjectMocks
    private ListarReceitasDoUsuarioService listarReceitasDoUsuarioService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;

    @Test
    @DisplayName("Deve listar as receitas do usuário corretamente")
    void deveListarReceitasUsuario(){
        Usuario usuario = UsuarioFactory.get();
        Usuario usuario2 = UsuarioFactory.get();
        Pageable pageable = Pageable.ofSize(10);
        Receita receita = ReceitaFactory.get();
        receita.setCriador(usuario2);
        receita.adicionarAlimento(AlimentoFactory.getBuilder(), "100");

        List<Receita> receitas = new ArrayList<>();
        receitas.add(receita);

        Page<Receita> receitasPage = new PageImpl<>(receitas);

        when(receitaRepository.findAllowedByUsuarioId(usuario2.getId(), usuario, pageable)).thenReturn(receitasPage);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(receitaJaAvaliadaValidator.validar(receita)).thenReturn(true);

        Page<ReceitaResponse> response = listarReceitasDoUsuarioService.listar(usuario2.getId(), pageable);
        List<ReceitaResponse> responseList = response.getContent();

        assertEquals(receita.getId(), responseList.get(0).getId());
    }

}
