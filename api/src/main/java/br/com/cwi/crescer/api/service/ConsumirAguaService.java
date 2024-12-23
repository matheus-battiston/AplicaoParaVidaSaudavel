package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AguaRequest;
import br.com.cwi.crescer.api.domain.Agua;
import br.com.cwi.crescer.api.repository.AguaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.validator.ConsumirAguaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.mapper.AguaMapper.toEntity;

@Service
public class ConsumirAguaService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private NowService nowService;

    @Autowired
    private AguaRepository aguaRepository;

    @Autowired
    private ConsumirAguaValidator consumirAguaValidator;

    @Autowired
    private ConquistasService conquistasService;

    public static final int DIAS_A_SUBTRAIR = 1;
    public static final Long PONTOS_POR_BATER_META_DIARIA_DE_AGUA_EM_STREAK = 40L;
    public static final Long PONTOS_POR_BATER_META_DIARIA_DE_AGUA_FORA_DE_STREAK = 20L;

    public void consumir(AguaRequest request){
        Usuario usuario = usuarioAutenticadoService.get();
        Agua agua = aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData()).orElse(toEntity(request));
        boolean jaBateuMetaHoje = agua.getQuantidade() >= usuario.getAguaRecomendacao();
        boolean bateuMetaOntem = aguaRepository
                .findByUsuario_IdAndDataRegistro(usuario.getId(), request.getData().minusDays(DIAS_A_SUBTRAIR))
                .orElse(new Agua(null,0,null,null))
                .getQuantidade() >= usuario.getAguaRecomendacao();

        consumirAguaValidator.validar(request.getQuantidade(), agua.getQuantidade());

        agua.setUsuario(usuario);
        agua.setQuantidade(agua.getQuantidade()+ request.getQuantidade());

        if(agua.getDataRegistro().isEqual(nowService.nowDate())){
            conquistasService.agua(usuario);
        }

        if(agua.getDataRegistro().isEqual(nowService.nowDate())
                && !jaBateuMetaHoje && bateuMetaOntem){
            usuario.adicionarPontos(PONTOS_POR_BATER_META_DIARIA_DE_AGUA_EM_STREAK);
        } else if (agua.getDataRegistro().isEqual(nowService.nowDate())
                && !jaBateuMetaHoje) {
            usuario.adicionarPontos(PONTOS_POR_BATER_META_DIARIA_DE_AGUA_FORA_DE_STREAK);
        }

        conquistasService.pontos(usuario);
        aguaRepository.save(agua);
    }
}
