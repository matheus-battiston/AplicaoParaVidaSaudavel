package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.AlimentoRequest;
import br.com.cwi.crescer.api.controller.response.AlimentoResponse;
import br.com.cwi.crescer.api.domain.Alimento;

public class AlimentoMapper {
    public static Alimento toEntity(AlimentoRequest request){
        return Alimento.builder()
                .nome(request.getNome())
                .calorias(request.getCalorias())
                .carboidratos(request.getCarboidratos())
                .proteinas(request.getProteinas())
                .lipidios(request.getLipidios())
                .build();
    }
    public static AlimentoResponse toResponse(Alimento alimento) {
        return AlimentoResponse.builder()
                .id(alimento.getId())
                .nome(alimento.getNome())
                .calorias(alimento.getCalorias())
                .proteinas(alimento.getProteinas())
                .carboidratos(alimento.getCarboidratos())
                .lipidios(alimento.getLipidios())
                .comunidade(alimento.isComunidade())
                .quantidade("100")
                .build();
    }
}
