package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.RelatorioResponse;
import br.com.cwi.crescer.api.domain.Agua;
import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.repository.AguaRepository;
import br.com.cwi.crescer.api.repository.RefeicaoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GerarRelatorioService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private RefeicaoRepository refeicaoRepository;
    @Autowired
    private AguaRepository aguaRepository;



    public RelatorioResponse gerar(LocalDate dia){
        Usuario usuario = usuarioAutenticadoService.get();

        List<Refeicao> refeicoes = refeicaoRepository.findByDiaAndUsuario_Id(dia, usuario.getId());
        RelatorioResponse relatorioResponse = new RelatorioResponse(0,0,0,0,0,
        usuario.getAguaRecomendacao(),usuario.getCaloriasRecomendacao(), usuario.getProteinasRecomendacao(), usuario.getCarboidratosRecomendacao(), usuario.getLipidiosRecomendacao());

        for (Refeicao ref : refeicoes) {
            relatorioResponse.setCalorias(relatorioResponse.getCalorias()+ ref.getCalorias());
            relatorioResponse.setProteinas(relatorioResponse.getProteinas()+ ref.getProteinas());
            relatorioResponse.setCarboidratos(relatorioResponse.getCarboidratos()+ ref.getCarboidratos());
            relatorioResponse.setLipidios(relatorioResponse.getLipidios()+ ref.getLipidios());
        }

        int aguaConsumida = aguaRepository.findByUsuario_IdAndDataRegistro(usuario.getId(),dia).orElse(new Agua()).getQuantidade();

        relatorioResponse.setAgua(aguaConsumida);

        return relatorioResponse;
    }
}
