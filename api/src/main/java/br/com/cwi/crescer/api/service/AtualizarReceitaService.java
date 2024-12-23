package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Receita;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import org.springframework.stereotype.Service;

@Service
public class AtualizarReceitaService {

    public void atualizar(Receita receita) {
        receita.setCalorias(0);
        receita.setCarboidratos(0);
        receita.setProteinas(0);
        receita.setLipidios(0);

        for (ReceitaAlimento alimento : receita.getAlimentos()) {
            float valorDaPorcao = Integer.parseInt(alimento.getQuantidade())/100.0F;
            receita.setCalorias(Math.round(Float.parseFloat(alimento.getAlimento().getCalorias())*valorDaPorcao)
                    + receita.getCalorias());
            receita.setProteinas(Math.round(Float.parseFloat(alimento.getAlimento().getProteinas())*valorDaPorcao)
                    + receita.getProteinas());
            receita.setCarboidratos(
                    Math.round(Float.parseFloat(alimento.getAlimento().getCarboidratos())*valorDaPorcao)
                    + receita.getCarboidratos());
            receita.setLipidios(
                    Math.round(Float.parseFloat(alimento.getAlimento().getLipidios())*valorDaPorcao)
                    + receita.getLipidios());
        }
    }
}
