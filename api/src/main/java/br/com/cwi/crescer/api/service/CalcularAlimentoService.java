package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Alimento;
import br.com.cwi.crescer.api.domain.ReceitaAlimento;
import org.springframework.stereotype.Service;

@Service
public class CalcularAlimentoService {
    public Alimento calcularNutrientes(ReceitaAlimento receitaAlimento) {
        int valorPorQuantidade = Integer.parseInt(receitaAlimento.getQuantidade())/100;
        Alimento alimento = receitaAlimento.getAlimento();
        alimento.setCalorias(String.valueOf(Double.parseDouble(alimento.getCalorias())*valorPorQuantidade));
        alimento.setProteinas(String.valueOf(Double.parseDouble(alimento.getProteinas())*valorPorQuantidade));
        alimento.setCarboidratos(String.valueOf(Double.parseDouble(alimento.getCarboidratos())*valorPorQuantidade));
        alimento.setLipidios(String.valueOf(Double.parseDouble(alimento.getLipidios())*valorPorQuantidade));
        return alimento;
    }
}
