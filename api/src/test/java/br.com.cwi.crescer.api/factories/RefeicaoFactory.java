package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.controller.request.RefeicaoRequest;
import br.com.cwi.crescer.api.domain.Refeicao;

import java.time.LocalDate;
import java.util.ArrayList;

import static br.com.cwi.crescer.api.domain.Periodo.JA;
import static br.com.cwi.crescer.api.domain.Periodo.LM;

public class RefeicaoFactory {
    public static Refeicao getBuilder(){
        return Refeicao.builder()
                .id(SimpleFactory.getRandomLong())
                .dia(LocalDate.now())
                .periodo(LM)
                .proteinas(12)
                .carboidratos(12)
                .lipidios(12)
                .calorias(12)
                .alimentos(new ArrayList<>())
                .build();
    }

    public static RefeicaoRequest getRequest(){
        return RefeicaoRequest.builder()
                .dia(LocalDate.now())
                .periodo(JA)
                .build();
    }
}
