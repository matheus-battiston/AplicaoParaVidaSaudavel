package br.com.cwi.crescer.api.service;


import br.com.cwi.crescer.api.controller.response.ConquistasProgressoResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.factories.ConquistaFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.mapper.ConquistaMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ListarConquistasServiceTest {
    @InjectMocks
    private ListarConquistasService tested;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve listar o progresso das conquistas do usuario")
    void deveListarProgressoDeConquistas(){
        Long progresso = 5L;
        Usuario usuario = UsuarioFactory.get();
        Conquista conquista = ConquistaFactory.get();

        ConquistaUsuario mapeado = ConquistaMapper.toConquistaUsuario(conquista, usuario);
        mapeado.setProgresso(progresso);
        usuario.getConquistas().add(mapeado);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        List<ConquistasProgressoResponse> response = tested.listar();

        assertEquals(1, response.size());
        assertEquals(progresso, response.get(0).getProgresso());
        assertEquals(mapeado.getConquista().getValor(), response.get(0).getObjetivo());
    }
}
