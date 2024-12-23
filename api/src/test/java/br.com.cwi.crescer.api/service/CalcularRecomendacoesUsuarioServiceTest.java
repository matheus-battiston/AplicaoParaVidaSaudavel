package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.AtividadeFisica;
import br.com.cwi.crescer.api.security.domain.Meta;
import br.com.cwi.crescer.api.security.domain.Sexo;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static java.lang.Double.parseDouble;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalcularRecomendacoesUsuarioServiceTest {
    @InjectMocks
    private CalcularRecomendacoesUsuarioService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve calcular recomendados")
    void deveCalcularRecomendacoes(){
        String imcEsperado = "30.86";
        Integer gastoCaloricoEsperado = 2186;
        Integer aguaRecomendada = 3500;
        Integer cnsumoCaloricoRecomendado = 2186;
        Integer proteinasEsperado = 100;
        Integer lipidiosEsperado = 70;

        Usuario usuario = UsuarioFactory.get();
        usuario.setAltura(180);
        usuario.setPeso("100");
        usuario.setAtividadeFisica(AtividadeFisica.P);
        usuario.setMeta(Meta.M);
        usuario.setSexo(Sexo.F);
        usuario.setDataNascimento(LocalDate.of(1997,9,17));

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        tested.calcular();

        verify(usuarioRepository).save(usuario);

        String imc = usuario.getImc();
        String imcFormatado = imc.replace(',', '.');

        assertEquals(parseDouble(imcEsperado), parseDouble(imcFormatado));
        assertEquals(gastoCaloricoEsperado, usuario.getGastoCalorico());
        assertEquals(aguaRecomendada, usuario.getAguaRecomendacao());
        assertEquals(cnsumoCaloricoRecomendado, usuario.getCaloriasRecomendacao());
        assertEquals(proteinasEsperado, usuario.getProteinasRecomendacao());
        assertEquals(lipidiosEsperado, usuario.getLipidiosRecomendacao());

    }

    @Test
    @DisplayName("Deve calcular recomendados com meta P")
    void deveCalcularRecomendacoesComMetaP(){
        String imcEsperado = "30.86";
        Integer gastoCaloricoEsperado = 2186;
        Integer aguaRecomendada = 3500;
        Integer cnsumoCaloricoRecomendado = 1786;
        Integer proteinasEsperado = 140;
        Integer lipidiosEsperado = 70;

        Usuario usuario = UsuarioFactory.get();
        usuario.setAltura(180);
        usuario.setPeso("100");
        usuario.setAtividadeFisica(AtividadeFisica.P);
        usuario.setMeta(Meta.P);
        usuario.setSexo(Sexo.F);
        usuario.setDataNascimento(LocalDate.of(1997,9,17));

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        tested.calcular();

        verify(usuarioRepository).save(usuario);

        String imc = usuario.getImc();
        String imcFormatado = imc.replace(',', '.');

        assertEquals(parseDouble(imcEsperado), parseDouble(imcFormatado));
        assertEquals(gastoCaloricoEsperado, usuario.getGastoCalorico());
        assertEquals(aguaRecomendada, usuario.getAguaRecomendacao());
        assertEquals(cnsumoCaloricoRecomendado, usuario.getCaloriasRecomendacao());
        assertEquals(proteinasEsperado, usuario.getProteinasRecomendacao());
        assertEquals(lipidiosEsperado, usuario.getLipidiosRecomendacao());

    }

    @Test
    @DisplayName("Deve calcular recomendados com meta G")
    void deveCalcularRecomendacoesComMetaG(){
        String imcEsperado = "30.86";
        Integer gastoCaloricoEsperado = 2186;
        Integer aguaRecomendada = 3500;
        Integer cnsumoCaloricoRecomendado = 2586;
        Integer proteinasEsperado = 160;
        Integer lipidiosEsperado = 70;

        Usuario usuario = UsuarioFactory.get();
        usuario.setAltura(180);
        usuario.setPeso("100");
        usuario.setAtividadeFisica(AtividadeFisica.P);
        usuario.setMeta(Meta.G);
        usuario.setSexo(Sexo.F);
        usuario.setDataNascimento(LocalDate.of(1997,9,17));

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        tested.calcular();

        verify(usuarioRepository).save(usuario);

        String imc = usuario.getImc();
        String imcFormatado = imc.replace(',', '.');


        assertEquals(parseDouble(imcEsperado), parseDouble(imcFormatado));
        assertEquals(gastoCaloricoEsperado, usuario.getGastoCalorico());
        assertEquals(aguaRecomendada, usuario.getAguaRecomendacao());
        assertEquals(cnsumoCaloricoRecomendado, usuario.getCaloriasRecomendacao());
        assertEquals(proteinasEsperado, usuario.getProteinasRecomendacao());
        assertEquals(lipidiosEsperado, usuario.getLipidiosRecomendacao());

    }

    @Test
    @DisplayName("Deve calcular recomendados com sexo M")
    void deveCalcularRecomendacoesComSexoM(){
        String imcEsperado = "30.86";
        Integer gastoCaloricoEsperado = 2322;
        Integer aguaRecomendada = 3500;
        Integer cnsumoCaloricoRecomendado = 2722;
        Integer proteinasEsperado = 160;
        Integer lipidiosEsperado = 70;

        Usuario usuario = UsuarioFactory.get();
        usuario.setAltura(180);
        usuario.setPeso("100");
        usuario.setAtividadeFisica(AtividadeFisica.P);
        usuario.setMeta(Meta.G);
        usuario.setSexo(Sexo.M);
        usuario.setDataNascimento(LocalDate.of(1997,9,17));

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        tested.calcular();

        verify(usuarioRepository).save(usuario);

        String imc = usuario.getImc();
        String imcFormatado = imc.replace(',', '.');

        assertEquals(parseDouble(imcEsperado), parseDouble(imcFormatado));
        assertEquals(gastoCaloricoEsperado, usuario.getGastoCalorico());
        assertEquals(aguaRecomendada, usuario.getAguaRecomendacao());
        assertEquals(cnsumoCaloricoRecomendado, usuario.getCaloriasRecomendacao());
        assertEquals(proteinasEsperado, usuario.getProteinasRecomendacao());
        assertEquals(lipidiosEsperado, usuario.getLipidiosRecomendacao());

    }

    @Test
    @DisplayName("Deve calcular recomendados com atividade M")
    void deveCalcularRecomendacoesComAtividadeM(){
        String imcEsperado = "30.86";
        Integer gastoCaloricoEsperado = 2368;
        Integer aguaRecomendada = 3500;
        Integer cnsumoCaloricoRecomendado = 1968;
        Integer proteinasEsperado = 140;
        Integer lipidiosEsperado = 70;

        Usuario usuario = UsuarioFactory.get();
        usuario.setAltura(180);
        usuario.setPeso("100");
        usuario.setAtividadeFisica(AtividadeFisica.M);
        usuario.setMeta(Meta.P);
        usuario.setSexo(Sexo.F);
        usuario.setDataNascimento(LocalDate.of(1997,9,17));

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        tested.calcular();

        String imc = usuario.getImc();
        String imcFormatado = imc.replace(',', '.');

        verify(usuarioRepository).save(usuario);

        assertEquals(parseDouble(imcEsperado), parseDouble(imcFormatado));
        assertEquals(gastoCaloricoEsperado, usuario.getGastoCalorico());
        assertEquals(aguaRecomendada, usuario.getAguaRecomendacao());
        assertEquals(cnsumoCaloricoRecomendado, usuario.getCaloriasRecomendacao());
        assertEquals(proteinasEsperado, usuario.getProteinasRecomendacao());
        assertEquals(lipidiosEsperado, usuario.getLipidiosRecomendacao());

    }

    @Test
    @DisplayName("Deve calcular recomendados com atividade I")
    void deveCalcularRecomendacoesComAtividadeI(){
        String imcEsperado = "30.86";
        Integer gastoCaloricoEsperado = 2550;
        Integer aguaRecomendada = 3500;
        Integer cnsumoCaloricoRecomendado = 2150;
        Integer proteinasEsperado = 140;
        Integer lipidiosEsperado = 70;

        Usuario usuario = UsuarioFactory.get();
        usuario.setAltura(180);
        usuario.setPeso("100");
        usuario.setAtividadeFisica(AtividadeFisica.I);
        usuario.setMeta(Meta.P);
        usuario.setSexo(Sexo.F);
        usuario.setDataNascimento(LocalDate.of(1997,9,17));

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        tested.calcular();

        verify(usuarioRepository).save(usuario);

        String imc = usuario.getImc();
        String imcFormatado = imc.replace(',', '.');


        assertEquals(parseDouble(imcEsperado), parseDouble(imcFormatado));
        assertEquals(gastoCaloricoEsperado, usuario.getGastoCalorico());
        assertEquals(aguaRecomendada, usuario.getAguaRecomendacao());
        assertEquals(cnsumoCaloricoRecomendado, usuario.getCaloriasRecomendacao());
        assertEquals(proteinasEsperado, usuario.getProteinasRecomendacao());
        assertEquals(lipidiosEsperado, usuario.getLipidiosRecomendacao());

    }
}
