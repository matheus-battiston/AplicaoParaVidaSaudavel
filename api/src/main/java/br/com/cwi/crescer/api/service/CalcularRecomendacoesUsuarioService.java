package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.domain.AtividadeFisica;
import br.com.cwi.crescer.api.security.domain.Meta;
import br.com.cwi.crescer.api.security.domain.Sexo;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Float.parseFloat;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.YEARS;

@Service
public class CalcularRecomendacoesUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    private static final int AGUA_POR_KG = 35;
    private static final int VALOR_CARBO_PERDER_PESO = 2;
    private static final int VALOR_CARBO_MANTER_PESO = 4;
    private static final int VALOR_CARBO_GANHAR_MASSA = 5;
    private static final float VALOR_LIPIDIO_POR_KG = 0.7F;
    private static final int CALORIAS_A_ALTERAR = 400;
    private static final float VALOR_PROTEINA_PERDER_PESO = 1.4F;
    private static final float VALOR_PROTEINA_GANHAR_MASSA = 1.6F;
    private static final float COEFICIENTE_POUCA_ATIVIDADE_FISICA = 1.2F;
    private static final float COEFICIENTE_ATIVIDADE_FISICA_MODERADA = 1.3F;
    private static final float COEFICIENTE_ATIVIDADE_FISICA_INTENSA = 1.4F;

    public void calcular(){
        Usuario usuario = usuarioAutenticadoService.get();
        String pesoUsuario = usuario.getPeso();
        Meta metaUsuario = usuario.getMeta();

        Integer gastoCalorico = calcularGastoCalorico(usuario);

        Integer caloriasRecomendacao = calcularIngestaoCaloricaRecomendada(metaUsuario, gastoCalorico);
        Integer aguaRecomendacao = calcularAguaRecomendada(pesoUsuario);
        Integer proteinasRecomendacao = calcularProteinasRecomendadas(metaUsuario, pesoUsuario);
        Integer carboidratosRecomendacao = calcularCarboidratosRecomendados(metaUsuario, pesoUsuario);
        Integer lipidiosRecomendacao = calcularLipidiosRecomendados(pesoUsuario);

        usuario.setGastoCalorico(gastoCalorico);
        usuario.setAguaRecomendacao(aguaRecomendacao);
        usuario.setCaloriasRecomendacao(caloriasRecomendacao);
        usuario.setProteinasRecomendacao(proteinasRecomendacao);
        usuario.setCarboidratosRecomendacao(carboidratosRecomendacao);
        usuario.setLipidiosRecomendacao(lipidiosRecomendacao);

        usuario.calcularIMC();
        usuarioRepository.save(usuario);
    }

    private Integer calcularIngestaoCaloricaRecomendada(Meta metaUsuario, Integer gastoCalorico) {
        if (metaUsuario.equals(Meta.M)) return gastoCalorico;
        if (metaUsuario.equals(Meta.P)) return gastoCalorico-CALORIAS_A_ALTERAR;
        return gastoCalorico+CALORIAS_A_ALTERAR;
    }

    private Integer calcularCarboidratosRecomendados(Meta meta,String peso) {
        if(meta.equals(Meta.M))
            return Math.round((parseFloat(peso) * VALOR_CARBO_MANTER_PESO));

        else if(meta.equals(Meta.P))
            return Math.round((parseFloat(peso) * VALOR_CARBO_PERDER_PESO));

        return Math.round((parseFloat(peso) * VALOR_CARBO_GANHAR_MASSA));
    }

    private Integer calcularLipidiosRecomendados(String peso) {
        return Math.round((parseFloat(peso)*VALOR_LIPIDIO_POR_KG));
    }

    private Integer calcularAguaRecomendada(String peso) {
        return Math.round((parseFloat(peso) * AGUA_POR_KG));
    }


    private Integer calcularProteinasRecomendadas(Meta meta, String peso) {
        if(meta.equals(Meta.M))
            return Math.round(parseFloat(peso));

        else if(meta.equals(Meta.P))
            return Math.round((parseFloat(peso) * VALOR_PROTEINA_PERDER_PESO));

        return Math.round((parseFloat(peso) * VALOR_PROTEINA_GANHAR_MASSA));
    }



    private float getCoeficienteAtividadeFisica(AtividadeFisica atividadeFisica){
        if(atividadeFisica.equals(AtividadeFisica.P)){
            return COEFICIENTE_POUCA_ATIVIDADE_FISICA;
        } else if(atividadeFisica.equals(AtividadeFisica.M)){
            return COEFICIENTE_ATIVIDADE_FISICA_MODERADA;
        } else return COEFICIENTE_ATIVIDADE_FISICA_INTENSA;
    }

    private Integer calcularGastoCalorico(Usuario usuario){
        final long IDADE_USUARIO = YEARS.between(usuario.getDataNascimento(), now());

        if(usuario.getSexo().equals(Sexo.M)) {
            return Math.round(
                    (66 + (13.8F * parseFloat(usuario.getPeso())))
                    + (5 * usuario.getAltura() - (6.8F * IDADE_USUARIO)) * getCoeficienteAtividadeFisica(usuario.getAtividadeFisica()));
        }

        return Math.round(
                ((655 + (9.6F * parseFloat(usuario.getPeso())))
                + (1.8F *usuario.getAltura()) - (4.7F * IDADE_USUARIO)) * getCoeficienteAtividadeFisica(usuario.getAtividadeFisica()));
    }
}
