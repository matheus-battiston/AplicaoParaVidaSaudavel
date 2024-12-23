package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.repository.AvaliacaoReceitaRepository;
import br.com.cwi.crescer.api.repository.ReceitaAlimentoRepository;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.UsuarioDonoReceitaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.factories.ReceitaFactory.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoverReceitaServiceTest {
    @InjectMocks
    private RemoverReceitaService removerReceitaService;

    @Mock
    private BuscarReceitaService buscarReceitaService;

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private ReceitaAlimentoRepository receitaAlimentoRepository;

    @Mock
    private AvaliacaoReceitaRepository avaliacaoReceitaRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioDonoReceitaValidator usuarioDonoReceitaValidator;

    @Test
    @DisplayName("Deve remover receita corretamente")
    void deveRemoverReceita(){
        Receita receita = get();
        Usuario usuario = receita.getUsuario();
        usuario.adicionarReceita(receita);

        when(buscarReceitaService.porId(receita.getId())).thenReturn(receita);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);


        removerReceitaService.remover(receita.getId());

        verify(receitaRepository).delete(receita);
        verify(avaliacaoReceitaRepository).deleteAllByReceita(receita);
        verify(usuarioDonoReceitaValidator).validar(usuario, usuario);

        assertEquals(0, usuario.getReceitas().size());
    }
}
