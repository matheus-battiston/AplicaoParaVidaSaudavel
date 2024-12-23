package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.RelatorioResponse;
import br.com.cwi.crescer.api.domain.Agua;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.factories.RefeicaoFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.AguaRepository;
import br.com.cwi.crescer.api.repository.RefeicaoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class GerarRelatorioServiceTest {
    @InjectMocks
    private GerarRelatorioService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private RefeicaoRepository refeicaoRepository;

    @Mock
    private AguaRepository aguaRepository;

    @Test
    @DisplayName("Deve gerar um relatorio por dia")
    void deveGerarRelatorioDia(){
        Integer quantidadeAgua = 3000;
        LocalDate hoje = LocalDate.now();
        Usuario usuario = UsuarioFactory.get();
        Refeicao refeicao = RefeicaoFactory.getBuilder();
        Agua agua = new Agua();
        agua.setDataRegistro(hoje);
        agua.setUsuario(usuario);
        agua.setQuantidade(quantidadeAgua);
        usuario.getAguas().add(agua);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(refeicaoRepository.findByDiaAndUsuario_Id(hoje, usuario.getId())).thenReturn(List.of(refeicao));
        when(aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(),hoje)).thenReturn(Optional.of(agua));

        RelatorioResponse response = tested.gerar(hoje);

        assertEquals(refeicao.getLipidios(), response.getLipidios());
        assertEquals(refeicao.getProteinas(), response.getProteinas());
        assertEquals(refeicao.getCalorias(), response.getCalorias());
        assertEquals(refeicao.getCarboidratos(), response.getCarboidratos());
        assertEquals(quantidadeAgua ,response.getAgua());
    }
}
