package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ReceitaAlimentoRequest;
import br.com.cwi.crescer.api.controller.request.ReceitaRequest;
import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import br.com.cwi.crescer.api.factories.AlimentoFactory;
import br.com.cwi.crescer.api.factories.ReceitaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ReceitaAlimentoRepository;
import br.com.cwi.crescer.api.repository.ReceitaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.UsuarioDonoReceitaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EditarReceitaServiceTest {
    @InjectMocks
    private EditarReceitaService editarReceitaService;

    @Mock
    private BuscarReceitaService buscarReceitaService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private BuscarAlimentoService buscarAlimentoService;

    @Mock
    private UsuarioDonoReceitaValidator usuarioDonoReceitaValidator;

    @Mock
    private ReceitaAlimentoRepository receitaAlimentoRepository;

    @Mock
    private AtualizarReceitaService atualizarReceitaService;

    @Captor
    private ArgumentCaptor<ReceitaAlimento> receitaAlimentoArgumentCaptor;

    @Test
    @DisplayName("Deve editar uma receita existente corretamente")
    void deveEditarReceitaExistente(){
        Usuario usuario = UsuarioFactory.get();
        Receita receita = ReceitaFactory.get();
        Alimento alimento = AlimentoFactory.getBuilder();

        List<ReceitaAlimentoRequest> receitaAlimentoRequest = new ArrayList<>();
        ReceitaAlimentoRequest alimentoRequest = new ReceitaAlimentoRequest();
        alimentoRequest.setId(alimento.getId());
        alimentoRequest.setQuantidade("100");
        receitaAlimentoRequest.add(alimentoRequest);

        ReceitaRequest request = new ReceitaRequest();
        request.setTitulo("receita teste");
        request.setPrivado(false);
        request.setImagem("www.teste.com.br");
        request.setDescricao("descricao teste");
        request.setCopia(false);
        request.setModoPreparo("testando");
        request.setAlimentos(receitaAlimentoRequest);

        when(buscarReceitaService.porId(receita.getId())).thenReturn(receita);
        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(buscarAlimentoService.porId(alimento.getId())).thenReturn(alimento);

        editarReceitaService.editar(receita.getId(), request);

        verify(receitaRepository).save(receita);
        verify(receitaAlimentoRepository).deleteAllByReceita_Id(receita.getId());
        verify(usuarioDonoReceitaValidator).validar(receita.getUsuario(), usuario);
        verify(receitaAlimentoRepository).save(receitaAlimentoArgumentCaptor.capture());
        verify(atualizarReceitaService).atualizar(receita);

        ReceitaAlimento receitaAlimento = receitaAlimentoArgumentCaptor.getValue();

        assertEquals(alimento, receitaAlimento.getAlimento());
        assertEquals(alimentoRequest.getQuantidade(), receitaAlimento.getQuantidade());
        assertEquals(receita, receitaAlimento.getReceita());
        assertEquals(request.getTitulo(), receita.getTitulo());
        assertEquals(request.getTitulo(), receita.getTitulo());
        assertEquals(request.getTitulo(), receita.getTitulo());
        assertEquals(request.getDescricao(), receita.getDescricao());
        assertEquals(request.getPrivado(), receita.isPrivado());
        assertEquals(request.getImagem(), receita.getImagem());
        assertEquals(request.getModoPreparo(), receita.getModoPreparo());
        assertEquals(alimento, receita.getAlimentos().get(0).getAlimento());
        assertEquals(request.getCopia(), receita.isCopia());

    }
}
