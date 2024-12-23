package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Agua;
import br.com.cwi.crescer.api.domain.ConquistaUsuario;
import br.com.cwi.crescer.api.domain.TipoConquista;
import br.com.cwi.crescer.api.repository.AguaRepository;
import br.com.cwi.crescer.api.repository.ConquistaUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.cwi.crescer.api.domain.TipoConquista.*;
import static java.util.Objects.nonNull;

@Service
public class ConquistasService {

    public static final Long PONTOS_CONQUISTA_BRONZE = 250L;
    public static final Long PONTOS_CONQUISTA_PRATA = 500L;
    public static final Long PONTOS_CONQUISTA_OURO = 1000L;
    public static final Long PONTOS_CONQUISTA_PLATINA = 2000L;

    public static final Integer TOTAL_DE_CONQUISTAS = 20;

    @Autowired
    private NowService nowService;
    @Autowired
    private ConquistaUsuarioRepository conquistaUsuarioRepository;

    @Autowired
    private AguaRepository aguaRepository;

    public void checarTodasConquistas(Usuario usuario){
        Long conquistasObtidas = conquistaUsuarioRepository.countDistinctByUsuarioAndDesbloqueadaIsTrue(usuario);
        if (conquistasObtidas == (TOTAL_DE_CONQUISTAS-1)) {
            ConquistaUsuario conquistaUsuario = conquistaUsuarioRepository.findFirstByUsuarioAndConquistaTipo(usuario, CONQUISTA);
            conquistaUsuario.setDesbloqueada(true);
            conquistaUsuario.setDataAlteracao(nowService.nowDate());
            usuario.adicionarPontos(PONTOS_CONQUISTA_PLATINA);
            pontos(usuario);

        }

    }

    public void checarConquista(ConquistaUsuario conquistaUsuario){
        if (conquistaUsuario.getProgresso() >= conquistaUsuario.getConquista().getValor()){
            conquistaUsuario.setDesbloqueada(true);
            ConquistaUsuario conquistador = conquistaUsuarioRepository.findFirstByUsuarioAndConquistaTipo(conquistaUsuario.getUsuario(), CONQUISTA);
            conquistador.setProgresso(conquistador.getProgresso() + 1);
            checarTodasConquistas(conquistaUsuario.getUsuario());
            switch (conquistaUsuario.getConquista().getCategoria()){
                case BRONZE:
                    conquistaUsuario.getUsuario().adicionarPontos(PONTOS_CONQUISTA_BRONZE);
                    pontos(conquistaUsuario.getUsuario());
                    break;
                case PRATA:
                    conquistaUsuario.getUsuario().adicionarPontos(PONTOS_CONQUISTA_PRATA);
                    pontos(conquistaUsuario.getUsuario());
                    break;
                case OURO:
                    conquistaUsuario.getUsuario().adicionarPontos(PONTOS_CONQUISTA_OURO);
                    pontos(conquistaUsuario.getUsuario());
                    break;
                case PLATINA:
                    conquistaUsuario.getUsuario().adicionarPontos(PONTOS_CONQUISTA_PLATINA);
                    pontos(conquistaUsuario.getUsuario());
                    break;
            }
        }
    }

    public void agua(Usuario usuario) {
        int aguaConsumida = aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), nowService.nowDate()).orElse(new Agua()).getQuantidade();
        int recomendacao = usuario.getAguaRecomendacao();

        if (aguaConsumida >= recomendacao){
            List<ConquistaUsuario> conquistas = conquistaUsuarioRepository.findAllByUsuarioAndConquistaTipo(usuario, AGUA);
            conquistas.forEach(this::progressoStreakDias);
        }
    }

    public void conquistasQuantidadeDeAcoes(Usuario usuario, TipoConquista tipoConquista) {
        List<ConquistaUsuario> conquistas = conquistaUsuarioRepository.findAllByUsuarioAndConquistaTipo(usuario, tipoConquista);
        conquistas.forEach(this::progressoQuantidadeBruta);

    }

    public void progressoStreakDias(ConquistaUsuario conquistaUsuario){
        if (!conquistaUsuario.isDesbloqueada()){
            if (nonNull(conquistaUsuario.getDataAlteracao()) && conquistaUsuario.getDataAlteracao().isEqual(nowService.nowDate().minusDays(1))){
                conquistaUsuario.adicionarProgresso();
            } else{
                conquistaUsuario.resetarProgresso();
            }

            checarConquista(conquistaUsuario);
        }
    }

    public void progressoQuantidadeBruta(ConquistaUsuario conquistaUsuario) {
        if (!conquistaUsuario.isDesbloqueada()){
            conquistaUsuario.adicionarProgresso();
            checarConquista(conquistaUsuario);
        }
    }

    public void pontos(Usuario usuario) {
        List<ConquistaUsuario> conquistas = conquistaUsuarioRepository
                .findAllByUsuarioAndConquistaTipo(usuario, PONTOS);
        conquistas.forEach(conquistaUsuario -> progressoPontos(usuario.getPontuacao(), conquistaUsuario));

    }

    public void progressoPontos(String pontuacao, ConquistaUsuario conquistaUsuario) {
        conquistaUsuario.setProgresso(Long.valueOf(pontuacao));
        checarConquista(conquistaUsuario);
    }
}
