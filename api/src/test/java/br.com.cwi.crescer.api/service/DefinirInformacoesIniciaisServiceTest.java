package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.controller.request.InformacoesIniciaisRequest;
import br.com.cwi.crescer.api.security.domain.AtividadeFisica;
import br.com.cwi.crescer.api.security.domain.Meta;
import br.com.cwi.crescer.api.security.domain.Sexo;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.DefinirInformacoesIniciaisService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.security.validator.ValidatorJaTemPesoInicial;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefinirInformacoesIniciaisServiceTest {
    @InjectMocks
    private DefinirInformacoesIniciaisService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private ValidatorJaTemPesoInicial validatorJaTemPesoInicial;
    @Mock
    private NowService nowService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CalcularRecomendacoesUsuarioService calcularRecomendacoesUsuarioService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @DisplayName("Deve definir as informacoes iniciais")
    void deveDefinirInfos(){
        Usuario usuario = UsuarioFactory.get();
        LocalDate hoje = LocalDate.now();
        Sexo sexo = Sexo.M;
        String peso = "100";
        Integer altura = 175;
        Meta meta = Meta.M;
        AtividadeFisica atividadeFisica = AtividadeFisica.I;

        InformacoesIniciaisRequest infos = new InformacoesIniciaisRequest();
        infos.setSexo(sexo);
        infos.setPeso(peso);
        infos.setAltura(altura);
        infos.setMeta(meta);
        infos.setAtividadeFisica(atividadeFisica);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(nowService.nowDate()).thenReturn(hoje);

        tested.definir(infos);

        verify(calcularRecomendacoesUsuarioService).calcular();
        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());

        Usuario capturado = usuarioArgumentCaptor.getValue();

        assertEquals(meta, capturado.getMeta());
        assertEquals(sexo, capturado.getSexo());
        assertEquals(altura, capturado.getAltura());
        assertEquals(atividadeFisica, capturado.getAtividadeFisica());
        assertEquals(peso, capturado.getPeso());
    }

}
