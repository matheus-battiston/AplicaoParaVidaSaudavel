package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.RefeicaoRequest;
import br.com.cwi.crescer.api.controller.response.RefeicaoResponse;
import br.com.cwi.crescer.api.domain.Refeicao;

import java.util.ArrayList;

public class RefeicaoMapper {
    public static Refeicao toEntity(RefeicaoRequest request){
        return Refeicao.builder()
                .dia(request.getDia())
                .periodo(request.getPeriodo())
                .alimentos(new ArrayList<>())
                .build();
    }

    public static RefeicaoResponse toResponse(Refeicao entity){
        return RefeicaoResponse.builder()
                .id(entity.getId())
                .lipidios(entity.getLipidios())
                .carboidratos(entity.getCarboidratos())
                .proteinas(entity.getProteinas())
                .calorias(entity.getCalorias())
                .dia(entity.getDia())
                .periodo(entity.getPeriodo())
                .alimentos(new ArrayList<>())
                .build();
    }
}
