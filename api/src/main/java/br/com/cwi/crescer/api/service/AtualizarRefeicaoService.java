package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Refeicao;
import br.com.cwi.crescer.api.domain.RefeicaoAlimento;
import org.springframework.stereotype.Service;

import static java.lang.Float.parseFloat;

@Service
public class AtualizarRefeicaoService {
    public void atualizar(Refeicao refeicao) {
        refeicao.setCalorias(0);
        refeicao.setCarboidratos(0);
        refeicao.setProteinas(0);
        refeicao.setLipidios(0);

        for (RefeicaoAlimento alimento : refeicao.getAlimentos()) {
            float valorDaPorcao = parseFloat(alimento.getQuantidade()) / 100;
            refeicao.setCalorias(
                    Math.round(parseFloat(alimento.getAlimento().getCalorias())*valorDaPorcao)
                            + refeicao.getCalorias());
            refeicao.setProteinas(
                    Math.round(
                            parseFloat(alimento.getAlimento().getProteinas())*valorDaPorcao) + refeicao.getProteinas());
            refeicao.setCarboidratos(
                    Math.round(
                            parseFloat(alimento.getAlimento().getCarboidratos())*valorDaPorcao) + refeicao.getCarboidratos());
            refeicao.setLipidios(
                    Math.round(
                           parseFloat(alimento.getAlimento().getLipidios())*valorDaPorcao) + refeicao.getLipidios());
        }
    }
}
