package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.RefeicaoPorReceitaRequest;
import br.com.cwi.crescer.api.domain.Refeicao;

import java.util.ArrayList;

public class RefeicaoPorReceitaMapper {
    public static Refeicao toEntity(RefeicaoPorReceitaRequest request){
        return Refeicao.builder()
                .dia(request.getDia())
                .periodo(request.getPeriodo())
                .alimentos(new ArrayList<>())
                .build();
    }
}
