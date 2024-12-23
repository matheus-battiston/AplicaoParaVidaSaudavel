package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.domain.AvaliacaoReceita;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceitaJaAvaliadaValidatorTest {

    @InjectMocks
    private ReceitaJaAvaliadaValidator receitaJaAvaliadaValidator;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve retornar true se a receita já foi avaliada")
    void deveRetornarTrueSeReceitaJaAvaliada(){
        Usuario usuario = UsuarioFactory.get();
        Receita receita = ReceitaFactory.get();

        AvaliacaoReceita avaliacaoReceita = new AvaliacaoReceita();
        avaliacaoReceita.setReceita(receita);
        avaliacaoReceita.setNota(4);
        avaliacaoReceita.setUsuario(usuario);

        List<AvaliacaoReceita> avaliacoesReceita = new ArrayList<>();
        avaliacoesReceita.add(avaliacaoReceita);

        receita.setAvaliacoes(avaliacoesReceita);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        boolean response = receitaJaAvaliadaValidator.validar(receita);

        assertTrue(response);
    }

    @Test
    @DisplayName("Deve retornar false se a receita não foi avaliada")
    void deveRetornarFalseSeReceitaNaoAvaliada(){
        Usuario usuario = UsuarioFactory.get();
        Receita receita = ReceitaFactory.get();

        List<AvaliacaoReceita> avaliacoesReceita = new ArrayList<>();

        receita.setAvaliacoes(avaliacoesReceita);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        boolean response = receitaJaAvaliadaValidator.validar(receita);

        assertFalse(response);
    }
}

